package ru.vs.iot.servers.ui.add_server

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.vs.core.decompose.view_model.ViewModel
import ru.vs.iot.servers.domain.ServersInteractor
import ru.vs.iot.servers.repository.Server

internal class AddServerViewModel(
    private val serversInteractor: ServersInteractor
) : ViewModel() {
    val events = Channel<Event>(Channel.BUFFERED)

    fun onClickSave(name: String, url: String) {
        viewModelScope.launch {
            val server = Server(0, name, url)
            serversInteractor.saveServer(server)
            events.send(Event.CloseScreen)
        }
    }
}

sealed class Event {
    object CloseScreen : Event()
}
