package ru.vs.iot.ui.screen.entities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import ru.vs.iot.domain.EntitiesInteractor
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.id.Id
import ru.vs.iot.repository.Server

class EntitiesViewModel(
    serversInteractor: ServersInteractor,
    private val entitiesInteractor: EntitiesInteractor
) : ViewModel() {
    val state: StateFlow<EntitiesScreenState> =
        serversInteractor.observeServers().mapLatest { servers -> servers.flatMap { getEntities(it) } }
            .map { EntitiesScreenState.ShowEntities(it) }
            .stateIn(viewModelScope, SharingStarted.Lazily, EntitiesScreenState.ShowEntities(emptyList()))

    private suspend fun getEntities(server: Server): List<EntitiesScreenState.EntityItem> {
        return runCatching {
            entitiesInteractor.getEntities(server).map { EntitiesScreenState.EntityItem(server, Id(it.key), it.value) }
        }.getOrElse { emptyList() }
    }
}
