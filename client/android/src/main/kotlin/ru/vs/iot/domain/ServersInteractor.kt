package ru.vs.iot.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.iot.repository.Server
import ru.vs.iot.repository.ServersRepository

interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
}

class ServersInteractorImpl(
    private val repository: ServersRepository
) : ServersInteractor {
    override fun observeServers(): Flow<List<Server>> {
        return repository.observeServers()
    }
}
