package ru.vs.iot.server.web

import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import ru.vs.iot.server.web.api.EntityApi
import ru.vs.iot.server.web.api.ServerApi
import ru.vs.iot.server.web.configuration.ContentNegotiationConfiguration

private const val SERVER_DEFAULT_PORT = 8080

interface WebServer {
    suspend fun run()
}

class WebServerImpl(
    private val contentNegotiationConfiguration: ContentNegotiationConfiguration,
    private val serverApi: ServerApi,
    private val entityApi: EntityApi,
) : WebServer {
    override suspend fun run() {
        withContext(CoroutineName("web-server")) {
            val environment = createEnvironment()
            val server = createEmbeddedServer(environment)
            server.start(true)
        }
    }

    private fun createEmbeddedServer(environment: ApplicationEngineEnvironment): ApplicationEngine =
        embeddedServer(Netty, environment)

    private fun CoroutineScope.createEnvironment(): ApplicationEngineEnvironment {
        return applicationEngineEnvironment {
            parentCoroutineContext = coroutineContext

            connector {
                host = "0.0.0.0"
                port = SERVER_DEFAULT_PORT
            }

            module {
                contentNegotiationConfiguration.apply { configure() }
                routing {
                    // TODO сделать через бинд инто сет в DI
                    serverApi.apply { bind() }
                    entityApi.apply { bind() }
                }
            }
        }
    }
}
