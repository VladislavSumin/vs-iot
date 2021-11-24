package ru.vs.iot.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.iot.api.EntitiesApi
import ru.vs.iot.api.EntitiesApiImpl
import ru.vs.iot.api.HttpClientFactory
import ru.vs.iot.api.HttpClientFactoryImpl
import ru.vs.iot.domain.EntitiesInteractor
import ru.vs.iot.domain.EntitiesInteractorImpl
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.domain.ServersInteractorImpl
import ru.vs.iot.repository.Database
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.repository.DatabaseFactoryImpl
import ru.vs.iot.ui.screen.add_server.AddServerViewModel
import ru.vs.iot.ui.screen.entities.EntitiesViewModel
import ru.vs.iot.ui.screen.servers.ServersViewModel

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
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i(), i()) }
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }

    // View models
    bindSingleton<ViewModelProvider.Factory> { DiViewModelFactory(directDI) }
    bindViewModel { ServersViewModel(i()) }
    bindViewModel { AddServerViewModel(i()) }
    bindViewModel { EntitiesViewModel(i(), i()) }
}
