package ru.vs.iot.entities.domain

import ru.vs.iot.entities.api.EntitiesApi
import ru.vs.iot.entities.dto.entity.EntitiesDTO
import ru.vs.iot.entities.dto.entity_state.EntitiesStatesDTO
import ru.vs.iot.servers.repository.Server

internal interface EntitiesInteractor {
    suspend fun getEntities(server: Server): EntitiesDTO
    suspend fun getEntitiesStates(server: Server): EntitiesStatesDTO
}

internal class EntitiesInteractorImpl(
    private val api: EntitiesApi
) : EntitiesInteractor {
    override suspend fun getEntities(server: Server): EntitiesDTO = api.getEntities(server)
    override suspend fun getEntitiesStates(server: Server): EntitiesStatesDTO = api.getEntitiesStates(server)
}
