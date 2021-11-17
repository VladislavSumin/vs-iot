package ru.vs.iot.di

import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient
import org.kodein.di.*
import ru.vs.iot.api.HttpClientFactory
import ru.vs.iot.api.HttpClientFactoryImpl
import ru.vs.iot.api.ServerApi
import ru.vs.iot.api.ServerApiImpl
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.domain.ServersInteractorImpl
import ru.vs.iot.repository.*
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.ui.screen.add_server.AddServerViewModel
import ru.vs.iot.ui.screen.servers.ServersViewModel

val rootModule = DI.Module("root") {
    // Api
    bindSingleton<HttpClientFactory> { HttpClientFactoryImpl() }
    bindSingleton { i<HttpClientFactory>().createHttpClient() }
    bindSingleton<ServerApi> { ServerApiImpl(i()) }

    // Database
    bindSingleton<DatabaseFactory> { DatabaseFactoryImpl(i()) }
    bindSingleton { instance<DatabaseFactory>().createDatabase() }
    bindSingleton { instance<Database>().serverQueries }

    // Repositories
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }

    // Interactors
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i()) }

    // View models
    bindSingleton<ViewModelProvider.Factory> { DiViewModelFactory(directDI) }
    bindViewModel { ServersViewModel(i()) }
    bindViewModel { AddServerViewModel(i()) }
}
