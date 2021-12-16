package ru.vs.rsub

import io.ktor.application.install
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.websocket.WebSockets
import ru.vs.rsub.connector.ktor_websocket.rSubWebSocket

@Suppress("MagicNumber")
fun main() {
    val rSubServer = RSubServer()

    val server = startServer(rSubServer)

    server.stop(100L, 100L)
}

private fun startServer(rSubServer: RSubServer): NettyApplicationEngine {
    return embeddedServer(Netty, port = 8080) {
        install(WebSockets)
        routing {
            rSubWebSocket(rSubServer)
        }
    }.start(false)
}
