package ru.vs.iot.ui.screen.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.repository.Server

class ServersViewModel(
    private val serversInteractor: ServersInteractor
) : ViewModel() {
    val state: StateFlow<ServersScreenState> = serversInteractor.observeServers()
        .flatMapLatest { observeServersConnectivity(it) }
        .map { ServersScreenState.ShowServersList(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, ServersScreenState.Loading)

    private suspend fun observeServersConnectivity(servers: List<Server>): Flow<List<ServersScreenState.ServerState>> =
        combine(servers.map { observeServerConnectivity(it) }) { it.toList() }

    private suspend fun observeServerConnectivity(server: Server) = flow {
        try {
            val aboutServerDTO = serversInteractor.getAboutServer(server)
            emit(ServersScreenState.ServerConnectivityState.Success(aboutServerDTO))
        } catch (expected: Exception) {
            emit(ServersScreenState.ServerConnectivityState.Error(expected))
        }
    }
        .onStart {
            emit(ServersScreenState.ServerConnectivityState.CheckingConnectivity)
        }
        .map {
            ServersScreenState.ServerState(server, it)
        }
}
