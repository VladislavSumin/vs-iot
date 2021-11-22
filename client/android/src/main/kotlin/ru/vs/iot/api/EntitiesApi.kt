package ru.vs.iot.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import ru.vs.iot.dto.server.entitiy.EntitiesDTO
import ru.vs.iot.repository.Server

interface EntitiesApi {
    suspend fun getEntities(server: Server): EntitiesDTO
}

class EntitiesApiImpl(
    private val client: HttpClient
) : EntitiesApi {
    override suspend fun getEntities(server: Server): EntitiesDTO {
        return client.get {
            url.takeFrom(server.url)
            url.encodedPath = "api/entities"
        }
    }
}
