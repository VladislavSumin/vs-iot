package ru.vs.iot.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.iot.entities.featureEntities
import ru.vs.iot.serialization.coreSerialization
import ru.vs.iot.server.domain.about.AboutServerInteractor
import ru.vs.iot.server.domain.about.AboutServerInteractorImpl
import ru.vs.iot.server.web.WebServer
import ru.vs.iot.server.web.WebServerImpl
import ru.vs.iot.server.web.api.ServerApi
import ru.vs.iot.server.web.configuration.ContentNegotiationConfiguration
import ru.vs.iot.server.web.configuration.ContentNegotiationConfigurationImpl

val Di = DI {
    // Core modules
    importOnce(Modules.coreSerialization())

    // Feature modules
    importOnce(Modules.featureEntities())

    // Interactors
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }

    // Web server
    bindSingleton<WebServer> { WebServerImpl(i(), i(), i()) }

    // Configurations
    bindSingleton<ContentNegotiationConfiguration> { ContentNegotiationConfigurationImpl(i()) }

    // Api
    bindSingleton { ServerApi(i()) }
}
