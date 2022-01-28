package ru.vs.iot.servers

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.servers.api.ServerApi
import ru.vs.iot.servers.api.ServerApiImpl
import ru.vs.iot.servers.domain.ServersInteractor
import ru.vs.iot.servers.domain.ServersInteractorImpl
import ru.vs.iot.servers.repository.Database
import ru.vs.iot.servers.repository.ServersRepository
import ru.vs.iot.servers.repository.ServersRepositoryImpl
import ru.vs.iot.servers.ui.add_server.AddServerViewModel
import ru.vs.iot.servers.ui.server.ServersViewModel

fun Modules.featureServers() = DI.Module("feature-servers") {
    // importOnce(Modules.featureDefaultServers())

    // Apis
    bindSingleton<ServerApi> { ServerApiImpl(i()) }

    // Database
    bindSingleton { instance<Database>().serverQueries }

    // Repositories
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }

    // Interactors
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i() /*, i()*/) }

    // View models
    bindProvider { ServersViewModel(i()) }
    bindProvider { AddServerViewModel(i()) }
}
