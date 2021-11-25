package ru.vs.iot.domain

import ru.vs.iot.api.EntitiesApi
import ru.vs.iot.entities.dto.EntitiesDTO
import ru.vs.iot.servers.repository.Server

interface EntitiesInteractor {
    suspend fun getEntities(server: Server): EntitiesDTO
}

class EntitiesInteractorImpl(
    private val api: EntitiesApi
) : EntitiesInteractor {
    override suspend fun getEntities(server: Server): EntitiesDTO = api.getEntities(server)
}
