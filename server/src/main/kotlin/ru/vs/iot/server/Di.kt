package ru.vs.iot.server

import org.kodein.di.*
import ru.vs.iot.server.web.WebServer
import ru.vs.iot.server.web.WebServerImpl

val Di = DI {
    bindSingleton<WebServer> { WebServerImpl() }
}

private inline fun <reified T : Any> DirectDIAware.i(tag: Any? = null): T = instance(tag)
