package ru.vs.iot.server

import org.kodein.di.DI
import org.kodein.di.DirectDIAware
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.iot.server.domain.about.AboutServerInteractor
import ru.vs.iot.server.domain.about.AboutServerInteractorImpl
import ru.vs.iot.server.web.WebServer
import ru.vs.iot.server.web.WebServerImpl
import ru.vs.iot.server.web.api.ServerApi
import ru.vs.iot.server.web.configuration.ContentNegotiationConfiguration
import ru.vs.iot.server.web.configuration.ContentNegotiationConfigurationImpl

// TODO написать плагинчик что бы автоматом фиксил количество i()
val Di = DI {
    // Interactors
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }

    // Web server
    bindSingleton<WebServer> { WebServerImpl(i(), i()) }

    // Configurations
    bindSingleton<ContentNegotiationConfiguration> { ContentNegotiationConfigurationImpl() }

    // Api
    bindSingleton { ServerApi(i()) }
}

private inline fun <reified T : Any> DirectDIAware.i(tag: Any? = null): T = instance(tag)
