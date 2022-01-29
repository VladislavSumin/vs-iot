package ru.vs.iot.entities.ui

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import ru.vs.iot.decompose.view_model.ViewModel
import ru.vs.iot.entities.domain.EntitiesInteractor
import ru.vs.iot.servers.domain.ServersInteractor
import ru.vs.iot.servers.repository.Server

internal class EntitiesViewModel(
    serversInteractor: ServersInteractor,
    private val entitiesInteractor: EntitiesInteractor
) : ViewModel() {
    val state: StateFlow<EntitiesScreenState> =
        serversInteractor.observeServers().mapLatest { servers -> servers.flatMap { getEntities(it) } }
            .map { EntitiesScreenState.ShowEntities(it) }
            .stateIn(viewModelScope, SharingStarted.Lazily, EntitiesScreenState.ShowEntities(emptyList()))

    private suspend fun getEntities(server: Server): List<EntitiesScreenState.EntityItem> {
        return runCatching {
            val entities = entitiesInteractor.getEntities(server)
            val entitiesStates = entitiesInteractor.getEntitiesStates(server)
            entities.map { EntitiesScreenState.EntityItem(server, it.key, it.value, entitiesStates[it.key]!!) }
        }.getOrElse { emptyList() }
    }
}
