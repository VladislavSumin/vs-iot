package ru.vs.rsub

import io.ktor.application.install
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.websocket.WebSockets

@Suppress("MagicNumber")
fun main() {
    val server = startServer()

    server.stop(100L, 100L)
}

private fun startServer(): NettyApplicationEngine {
    return embeddedServer(Netty, port = 8080) {
        install(WebSockets)
        routing {
            // rSubWebSocket(rSubServer)
        }
    }.start(false)
}
