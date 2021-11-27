package ru.vs.iot.entities.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import ru.vs.iot.entities.dto.entity.EntitiesDTO
import ru.vs.iot.servers.repository.Server

internal interface EntitiesApi {
    suspend fun getEntities(server: Server): EntitiesDTO
}

internal class EntitiesApiImpl(
    private val client: HttpClient
) : EntitiesApi {
    override suspend fun getEntities(server: Server): EntitiesDTO {
        return client.get {
            url.takeFrom(server.url)
            url.encodedPath = "api/entities"
        }
    }
}
