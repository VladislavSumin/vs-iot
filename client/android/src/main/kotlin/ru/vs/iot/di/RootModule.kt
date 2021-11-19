package ru.vs.iot.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.iot.api.HttpClientFactory
import ru.vs.iot.api.HttpClientFactoryImpl
import ru.vs.iot.api.ServerApi
import ru.vs.iot.api.ServerApiImpl
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.domain.ServersInteractorImpl
import ru.vs.iot.repository.Database
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.repository.DatabaseFactoryImpl
import ru.vs.iot.repository.DefaultServersRepository
import ru.vs.iot.repository.DefaultServersRepositoryImpl
import ru.vs.iot.repository.ServersRepository
import ru.vs.iot.repository.ServersRepositoryImpl
import ru.vs.iot.ui.screen.add_server.AddServerViewModel
import ru.vs.iot.ui.screen.servers.ServersViewModel

fun Modules.root() = DI.Module("root") {
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
    bindSingleton<DefaultServersRepository> { DefaultServersRepositoryImpl() }

    // Interactors
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i(), i()) }

    // View models
    bindSingleton<ViewModelProvider.Factory> { DiViewModelFactory(directDI) }
    bindViewModel { ServersViewModel(i()) }
    bindViewModel { AddServerViewModel(i()) }
}
