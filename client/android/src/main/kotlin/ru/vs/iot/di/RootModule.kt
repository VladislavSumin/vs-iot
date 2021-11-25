package ru.vs.iot.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.iot.api.EntitiesApi
import ru.vs.iot.api.EntitiesApiImpl
import ru.vs.iot.api.HttpClientFactory
import ru.vs.iot.api.HttpClientFactoryImpl
import ru.vs.iot.domain.EntitiesInteractor
import ru.vs.iot.domain.EntitiesInteractorImpl
import ru.vs.iot.repository.Database
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.repository.DatabaseFactoryImpl
import ru.vs.iot.ui.screen.entities.EntitiesViewModel

fun Modules.root() = DI.Module("root") {
    // Api
    bindSingleton<HttpClientFactory> { HttpClientFactoryImpl() }
    bindSingleton { i<HttpClientFactory>().createHttpClient() }
    bindSingleton<EntitiesApi> { EntitiesApiImpl(i()) }

    // Database
    bindSingleton<DatabaseFactory> { DatabaseFactoryImpl(i()) }
    bindSingleton { instance<DatabaseFactory>().createDatabase() }
    bindSingleton { instance<Database>().serverQueries }

    // Interactors
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }

    // View models
    bindViewModel { EntitiesViewModel(i(), i()) }
}
