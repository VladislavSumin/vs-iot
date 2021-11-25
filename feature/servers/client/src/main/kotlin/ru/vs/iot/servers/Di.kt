package ru.vs.iot.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inSet
import org.kodein.di.provider
import ru.vs.iot.default_servers.featureDefaultServers
import ru.vs.iot.di.Modules
import ru.vs.iot.di.bindViewModel
import ru.vs.iot.di.i
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.navigation.ui.destination.createDestination
import ru.vs.iot.servers.api.ServerApi
import ru.vs.iot.servers.api.ServerApiImpl
import ru.vs.iot.servers.domain.ServersInteractor
import ru.vs.iot.servers.domain.ServersInteractorImpl
import ru.vs.iot.servers.repository.ServersRepository
import ru.vs.iot.servers.repository.ServersRepositoryImpl
import ru.vs.iot.servers.ui.AddServer
import ru.vs.iot.servers.ui.Servers
import ru.vs.iot.servers.ui.add_server.AddServerScreen
import ru.vs.iot.servers.ui.add_server.AddServerViewModel
import ru.vs.iot.servers.ui.server.ServersScreen
import ru.vs.iot.servers.ui.server.ServersViewModel

fun Modules.featureServers() = DI.Module("feature-servers") {
    importOnce(Modules.featureDefaultServers())

    // Apis
    bindSingleton<ServerApi> { ServerApiImpl(i()) }

    // Repositories
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }

    // Interactors
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i(), i()) }

    // View models
    bindViewModel { ServersViewModel(i()) }
    bindViewModel { AddServerViewModel(i()) }

    // Navigation
    inSet<NavigationDestination> { provider { Screen.Servers.createDestination { ServersScreen() } } }
    inSet<NavigationDestination> { provider { Screen.AddServer.createDestination { AddServerScreen() } } }
}
