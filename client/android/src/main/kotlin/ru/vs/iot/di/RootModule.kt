package ru.vs.iot.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.*
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.domain.ServersInteractorImpl
import ru.vs.iot.repository.*
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.ui.screen.add_server.AddServerViewModel
import ru.vs.iot.ui.screen.servers.ServersViewModel

val rootModule = DI.Module("root") {
    bindSingleton<DatabaseFactory> { DatabaseFactoryImpl(i()) }
    bindSingleton { instance<DatabaseFactory>().createDatabase() }
    bindSingleton { instance<Database>().serverQueries }

    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }

    bindSingleton<ServersInteractor> { ServersInteractorImpl(i()) }

    bindSingleton<ViewModelProvider.Factory> { DiViewModelFactory(directDI) }
    bindViewModel { ServersViewModel(i()) }
    bindViewModel { AddServerViewModel(i()) }
}
