package ru.vs.iot.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.iot.api.ServerApi
import ru.vs.iot.repository.DefaultServersRepository
import ru.vs.iot.repository.Server
import ru.vs.iot.repository.ServersRepository

interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
    suspend fun saveServer(server: Server)
}

class ServersInteractorImpl(
    private val repository: ServersRepository,
    private val defaultServersRepository: DefaultServersRepository,
    private val api: ServerApi
) : ServersInteractor {
    override fun observeServers(): Flow<List<Server>> = repository.observeServers()
    override suspend fun saveServer(server: Server) = repository.insert(server)
}
