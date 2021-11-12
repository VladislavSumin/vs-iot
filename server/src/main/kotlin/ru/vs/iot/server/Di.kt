package ru.vs.iot.server

import org.kodein.di.*
import ru.vs.iot.server.web.WebServer
import ru.vs.iot.server.web.WebServerImpl
import ru.vs.iot.server.web.api.ServerApi

val Di = DI {
    bindSingleton<WebServer> { WebServerImpl(i()) }
    bindSingleton { ServerApi() }
}

private inline fun <reified T : Any> DirectDIAware.i(tag: Any? = null): T = instance(tag)
