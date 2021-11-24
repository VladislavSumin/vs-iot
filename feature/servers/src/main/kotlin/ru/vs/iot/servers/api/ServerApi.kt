package ru.vs.iot.servers.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import ru.vs.iot.dto.server.AboutServerDTO
import ru.vs.iot.servers.repository.Server

interface ServerApi {
    suspend fun getAboutServerInfo(server: Server): AboutServerDTO
}

internal class ServerApiImpl(
    private val client: HttpClient
) : ServerApi {
    override suspend fun getAboutServerInfo(server: Server): AboutServerDTO {
        return client.get {
            url.takeFrom(server.url)
            url.encodedPath = "api/server/about"
        }
    }
}
