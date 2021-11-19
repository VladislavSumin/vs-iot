package ru.vs.iot.default_servers.repository

interface DefaultServersRepository {
    suspend fun getDefaultServers(): List<DefaultServer>
}

data class DefaultServer(val name: String, val url: String)