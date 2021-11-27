package ru.vs.iot.server.web.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.coroutines.flow.first
import ru.vs.iot.entities.dto.entity.EntitiesDTO
import ru.vs.iot.entities.dto.entity.EntityDTO
import ru.vs.iot.id.Id
import ru.vs.iot.server.domain.entity.Entity
import ru.vs.iot.server.domain.entity.EntityInteractor

class EntityApi(
    private val entityInteractor: EntityInteractor,
) {
    fun Routing.bind() {
        getEntities()
    }

    private fun Routing.getEntities() = get("/api/entities") {
        val entities = entityInteractor.observeEntities().first().toDTO()
        call.respond(entities)
    }
}

private fun Map<Id, Entity>.toDTO(): EntitiesDTO = mapKeys { it.key.raw }.mapValues { it.value.toDTO() }
private fun Entity.toDTO() = EntityDTO()
