package ru.vs.iot.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.default_servers.featureDefaultServers
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.servers.api.ServerApi
import ru.vs.iot.servers.api.ServerApiImpl
import ru.vs.iot.servers.domain.ServersInteractor
import ru.vs.iot.servers.domain.ServersInteractorImpl
import ru.vs.iot.servers.repository.ServersRepository
import ru.vs.iot.servers.repository.ServersRepositoryImpl

fun Modules.featureServers() = DI.Module("feature-servers") {
    importOnce(Modules.featureDefaultServers())

    bindSingleton<ServerApi> { ServerApiImpl(i()) }
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i(), i()) }
}
