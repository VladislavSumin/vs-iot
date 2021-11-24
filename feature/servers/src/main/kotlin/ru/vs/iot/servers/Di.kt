package ru.vs.iot.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.servers.repository.ServersRepository
import ru.vs.iot.servers.repository.ServersRepositoryImpl

fun Modules.featureServers() = DI.Module("feature-servers") {
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }
}
