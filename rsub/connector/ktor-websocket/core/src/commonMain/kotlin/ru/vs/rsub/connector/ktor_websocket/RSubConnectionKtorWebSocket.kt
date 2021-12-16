package ru.vs.rsub.connector.ktor_websocket

import io.ktor.http.cio.websocket.DefaultWebSocketSession
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import ru.vs.rsub.RSubConnection

class RSubConnectionKtorWebSocket(
    private val session: DefaultWebSocketSession
) : RSubConnection {
    override val receive: Flow<String>
        get() = session.incoming.receiveAsFlow()
            .map { it as Frame.Text }
            .map { it.readText() }

    override suspend fun send(data: String) = session.send(Frame.Text(data))

    override suspend fun close() = session.close()
}
