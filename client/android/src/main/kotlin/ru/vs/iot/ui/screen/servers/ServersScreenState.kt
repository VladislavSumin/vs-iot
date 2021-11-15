package ru.vs.iot.ui.screen.servers

import ru.vs.iot.repository.Server

sealed class ServersScreenState {
    object Loading : ServersScreenState()
    data class ShowServersList(val servers: List<Server>) : ServersScreenState()
}
