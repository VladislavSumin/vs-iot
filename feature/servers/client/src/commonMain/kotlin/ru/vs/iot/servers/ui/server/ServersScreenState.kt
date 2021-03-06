package ru.vs.iot.servers.ui.server

import ru.vs.iot.servers.dto.AboutServerDTO
import ru.vs.iot.servers.repository.Server

internal sealed class ServersScreenState {
    object Loading : ServersScreenState()
    data class ShowServersList(val servers: List<ServerState>, val isRefreshing: Boolean) : ServersScreenState()

    data class ServerState(val server: Server, val connectivityState: ServerConnectivityState)

    sealed class ServerConnectivityState {
        object CheckingConnectivity : ServerConnectivityState()
        class Success(val aboutServer: AboutServerDTO) : ServerConnectivityState()
        class Error(val e: Exception) : ServerConnectivityState()
    }
}
