package ru.vs.iot.server

import org.kodein.di.*
import ru.vs.iot.server.domain.about.AboutServerInteractor
import ru.vs.iot.server.domain.about.AboutServerInteractorImpl
import ru.vs.iot.server.web.WebServer
import ru.vs.iot.server.web.WebServerImpl
import ru.vs.iot.server.web.api.ServerApi

//TODO написать плагинчик что бы автоматом фиксил количество i()
val Di = DI {
    // Interactors
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }

    // Web server
    bindSingleton<WebServer> { WebServerImpl(i()) }

    // Api
    bindSingleton { ServerApi(i()) }
}

private inline fun <reified T : Any> DirectDIAware.i(tag: Any? = null): T = instance(tag)
