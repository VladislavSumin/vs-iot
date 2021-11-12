package ru.vs.iot.server.web

import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import ru.vs.iot.server.web.api.ServerApi
import ru.vs.iot.server.web.configuration.ContentNegotiationConfiguration

interface WebServer {
    suspend fun run()
}

class WebServerImpl(
    private val contentNegotiationConfiguration: ContentNegotiationConfiguration,
    private val serverApi: ServerApi
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
                port = 8888
            }

            module {
                contentNegotiationConfiguration.apply { configure() }
                routing {
                    serverApi.apply { bind() }
                }
            }
        }
    }
}