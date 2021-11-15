package ru.vs.iot.ui.screen.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.vs.iot.domain.ServersInteractor

class ServersViewModel(
    private val serversInteractor: ServersInteractor
) : ViewModel() {
    val state: StateFlow<ServersScreenState> = serversInteractor.observeServers()
        .map { ServersScreenState.ShowServersList(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, ServersScreenState.Loading)
}
