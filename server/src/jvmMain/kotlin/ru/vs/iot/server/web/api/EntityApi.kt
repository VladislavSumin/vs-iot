package ru.vs.iot.server.web.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import ru.vs.iot.server.domain.entity.Entity
import ru.vs.iot.server.domain.entity.EntityInteractor

class EntityApi(
    private val entityInteractor: EntityInteractor,
) {
    fun Routing.bind() {
        getEntities()
    }

    private fun Routing.getEntities() = get("/api/entities") {
        val entities = entityInteractor.observeEntities().first()
            .mapKeys { it.key.raw }
            .mapValues { it.value.toDTO() }
        call.respond(entities)
    }
}

fun Entity.toDTO() = EntityDTO()

@Serializable
class EntityDTO
