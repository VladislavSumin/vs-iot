package ru.vs.iot.entities.domain

import ru.vs.iot.entities.api.EntitiesApi
import ru.vs.iot.entities.dto.EntitiesDTO
import ru.vs.iot.servers.repository.Server

internal interface EntitiesInteractor {
    suspend fun getEntities(server: Server): EntitiesDTO
}

internal class EntitiesInteractorImpl(
    private val api: EntitiesApi
) : EntitiesInteractor {
    override suspend fun getEntities(server: Server): EntitiesDTO = api.getEntities(server)
}
