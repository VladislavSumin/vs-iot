package ru.vs.iot.ui.screen.add_server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.servers.repository.Server

class AddServerViewModel(
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
