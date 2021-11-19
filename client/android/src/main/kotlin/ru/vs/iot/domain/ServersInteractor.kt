package ru.vs.iot.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.iot.AutoStartComponent
import ru.vs.iot.api.ServerApi
import ru.vs.iot.default_servers.domain.DefaultServersInteractor
import ru.vs.iot.default_servers.repository.DefaultServer
import ru.vs.iot.repository.Server
import ru.vs.iot.repository.ServersRepository

// TODO по хорошему AutoStartComponent нужно вешать на имлементацию, нужно подумать как красиво забиндить в кодеине
interface ServersInteractor : AutoStartComponent {
    fun observeServers(): Flow<List<Server>>
    suspend fun saveServer(server: Server)
}

class ServersInteractorImpl(
    private val repository: ServersRepository,
    private val defaultServersInteractor: DefaultServersInteractor,
    private val api: ServerApi
) : ServersInteractor {
    override fun observeServers(): Flow<List<Server>> = repository.observeServers()
    override suspend fun saveServer(server: Server) = repository.insert(server)

    override suspend fun start() {
        defaultServersInteractor.initializeDefaultServersIfNeeded { defaultServers ->
            defaultServers.forEach {
                repository.insert(it.toServer())
            }
        }
    }
}

private fun DefaultServer.toServer() = Server(0L, name, url)
