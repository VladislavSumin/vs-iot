package ru.vs.iot.ui.screen.entities

import ru.vs.iot.entities.dto.EntityDTO
import ru.vs.iot.id.Id
import ru.vs.iot.servers.repository.Server

sealed interface EntitiesScreenState {
    class ShowEntities(val entities: List<EntityItem>) : EntitiesScreenState

    data class EntityItem(val server: Server, val id: Id, val entityDTO: EntityDTO)
}
