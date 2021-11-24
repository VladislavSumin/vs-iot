package ru.vs.iot.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.iot.AutoStartComponent
import ru.vs.iot.default_servers.domain.DefaultServersInteractor
import ru.vs.iot.default_servers.repository.DefaultServer
import ru.vs.iot.dto.server.AboutServerDTO
import ru.vs.iot.servers.api.ServerApi
import ru.vs.iot.servers.repository.Server
import ru.vs.iot.servers.repository.ServersRepository

// TODO по хорошему AutoStartComponent нужно вешать на имлементацию, нужно подумать как красиво забиндить в кодеине
interface ServersInteractor : AutoStartComponent {
    fun observeServers(): Flow<List<Server>>
    suspend fun saveServer(server: Server)
    suspend fun deleteServer(server: Server)
    suspend fun getAboutServer(server: Server): AboutServerDTO
}

class ServersInteractorImpl(
    private val repository: ServersRepository,
    private val defaultServersInteractor: DefaultServersInteractor,
    private val api: ServerApi
) : ServersInteractor {
    override fun observeServers(): Flow<List<Server>> = repository.observeServers()
    override suspend fun saveServer(server: Server) = repository.insert(server)
    override suspend fun deleteServer(server: Server) = repository.delete(server)

    override suspend fun start() {
        defaultServersInteractor.initializeDefaultServersIfNeeded { defaultServers ->
            defaultServers.forEach {
                repository.insert(it.toServer())
            }
        }
    }

    override suspend fun getAboutServer(server: Server): AboutServerDTO = api.getAboutServerInfo(server)
}

private fun DefaultServer.toServer() = Server(0L, name, url)
