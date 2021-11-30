package ru.vs.iot.entities.ui

import ru.vs.iot.entities.dto.entity.EntityDTO
import ru.vs.iot.entities.dto.entity_state.EntityStateDTO
import ru.vs.iot.id.Id
import ru.vs.iot.servers.repository.Server

sealed interface EntitiesScreenState {
    class ShowEntities(val entities: List<EntityItem>) : EntitiesScreenState

    data class EntityItem(val server: Server, val id: Id, val entity: EntityDTO, val entityState: EntityStateDTO)
}
