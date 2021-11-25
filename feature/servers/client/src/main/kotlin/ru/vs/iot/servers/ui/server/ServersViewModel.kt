package ru.vs.iot.servers.ui.server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.vs.iot.dto.server.AboutServerDTO
import ru.vs.iot.servers.domain.ServersInteractor
import ru.vs.iot.servers.repository.Server
import ru.vs.iot.utils.onException

internal class ServersViewModel(
    private val serversInteractor: ServersInteractor
) : ViewModel() {
    // TODO придумать более адекватный способо управления состоянием обновления
    private val refreshState = MutableStateFlow(false)
    private val refreshEvent: Flow<Unit> = refreshState
        .filter { it }
        .map { }
        .onStart { emit(Unit) }

    // TODO по хорошему при удалении сервера не должно быть рефреша, но это когда нибудь никогда
    val state: StateFlow<ServersScreenState> = serversInteractor.observeServers()
        .flatMapLatest { servers -> refreshEvent.map { servers } }
        .flatMapLatest { observeServersConnectivity(it) }
        .combine(refreshState) { servers, isRefresh -> ServersScreenState.ShowServersList(servers, isRefresh) }
        .stateIn(viewModelScope, SharingStarted.Lazily, ServersScreenState.Loading)

    fun onRefresh() {
        viewModelScope.launch {
            refreshState.emit(true)
        }
    }

    fun onClickDeleteServer(server: Server) {
        viewModelScope.launch {
            serversInteractor.deleteServer(server)
        }
    }

    private suspend fun observeServersConnectivity(servers: List<Server>): Flow<List<ServersScreenState.ServerState>> =
        combine(servers.map { observeServerConnectivity(it) }) { it.toList() }
            .onCompletion { refreshState.emit(false) }

    private suspend fun observeServerConnectivity(server: Server) = flow {
        runCatching<AboutServerDTO> { serversInteractor.getAboutServer(server) }
            .onSuccess { emit(ServersScreenState.ServerConnectivityState.Success(it)) }
            .onException { emit(ServersScreenState.ServerConnectivityState.Error(it)) }
    }
        .onStart { emit(ServersScreenState.ServerConnectivityState.CheckingConnectivity) }
        .map { ServersScreenState.ServerState(server, it) }
}
