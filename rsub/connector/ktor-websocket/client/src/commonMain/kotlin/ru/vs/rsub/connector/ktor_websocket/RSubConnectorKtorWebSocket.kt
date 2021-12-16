package ru.vs.rsub.connector.ktor_websocket

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector

class RSubConnectorKtorWebSocket(
    private val client: HttpClient,
    private val host: String = "localhost",
    private val path: String = "/rSub",
    private val port: Int = 8080
) : RSubConnector {
    override suspend fun connect(): RSubConnection {
        val session = client.webSocketSession(
            method = HttpMethod.Get,
            host = host,
            port = port,
            path = path,
        ) {
            setAttributes {
                header(HttpHeaders.SecWebSocketProtocol, "rSub")
            }
        }
        return RSubConnectionKtorWebSocket(session)
    }
}
