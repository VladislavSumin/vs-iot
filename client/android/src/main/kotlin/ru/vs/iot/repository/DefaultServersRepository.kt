package ru.vs.iot.repository

interface DefaultServersRepository {
    suspend fun getDefaultServers(): List<Server>
}
