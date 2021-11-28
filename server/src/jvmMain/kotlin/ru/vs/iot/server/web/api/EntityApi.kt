package ru.vs.iot.server.web.api

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vs.iot.entities.dto.entity.EntitiesDTO
import ru.vs.iot.entities.dto.entity.EntityDTO
import ru.vs.iot.id.Id
import ru.vs.iot.server.domain.entity.Entity
import ru.vs.iot.server.domain.entity.EntityInteractor

class EntityApi(
    private val entityInteractor: EntityInteractor,
    private val json: Json
) {
    fun Routing.bind() {
        getEntities()
        getEntitiesStates()
    }

    private fun Routing.getEntities() = get("/api/entities") {
        val entities = entityInteractor.observeEntities().first().toDTO()
        call.respond(entities)
    }

    private fun Routing.getEntitiesStates() = get("/api/entities/states") {
        val states = entityInteractor.observeEntitiesState().first()
        val serializedStates = json.encodeToString(states)
        call.respondText(serializedStates, contentType = ContentType.Application.Json)
    }
}

private fun Map<Id, Entity>.toDTO(): EntitiesDTO = mapValues { it.value.toDTO() }
private fun Entity.toDTO() = EntityDTO()
