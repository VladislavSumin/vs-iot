package ru.vs.iot.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
}

class ServersInteractorImpl : ServersInteractor {
    override fun observeServers(): Flow<List<Server>> {
        return flowOf(listOf(
            Server(0, "Server 1", "https://localhost:8080"),
            Server(1, "Server 2", "https://sumin.ru:8080"),
        ) + (2 until 50).map {
            Server(it, "Generic server", "https://generic$it.server.com")
        })
    }
}

data class Server(
    val id: Int, val name: String, val address: String
)
