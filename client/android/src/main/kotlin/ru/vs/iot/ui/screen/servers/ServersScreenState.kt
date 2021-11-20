package ru.vs.iot.ui.screen.servers

import ru.vs.iot.dto.server.AboutServerDTO
import ru.vs.iot.repository.Server

sealed class ServersScreenState {
    object Loading : ServersScreenState()
    data class ShowServersList(val servers: List<ServerState>) : ServersScreenState()

    data class ServerState(val server: Server, val connectivityState: ServerConnectivityState)

    sealed class ServerConnectivityState {
        object CheckingConnectivity : ServerConnectivityState()
        class Success(val aboutServer: AboutServerDTO) : ServerConnectivityState()
        class Error(val e: Exception) : ServerConnectivityState()
    }
}
