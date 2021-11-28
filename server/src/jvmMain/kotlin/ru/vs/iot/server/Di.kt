package ru.vs.iot.server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.serialization.coreSerialization
import ru.vs.iot.server.domain.about.AboutServerInteractor
import ru.vs.iot.server.domain.about.AboutServerInteractorImpl
import ru.vs.iot.server.domain.entity.EntityInteractor
import ru.vs.iot.server.domain.entity.EntityInteractorImpl
import ru.vs.iot.server.web.WebServer
import ru.vs.iot.server.web.WebServerImpl
import ru.vs.iot.server.web.api.EntityApi
import ru.vs.iot.server.web.api.ServerApi
import ru.vs.iot.server.web.configuration.ContentNegotiationConfiguration
import ru.vs.iot.server.web.configuration.ContentNegotiationConfigurationImpl

// TODO написать плагинчик что бы автоматом фиксил количество i()
val Di = DI {
    // Modules
    importOnce(Modules.coreSerialization())

    // Interactors
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }
    bindSingleton<EntityInteractor> { EntityInteractorImpl() }

    // Web server
    bindSingleton<WebServer> { WebServerImpl(i(), i(), i()) }

    // Configurations
    bindSingleton<ContentNegotiationConfiguration> { ContentNegotiationConfigurationImpl(i()) }

    // Api
    bindSingleton { ServerApi(i()) }
    bindSingleton { EntityApi(i()) }
}
