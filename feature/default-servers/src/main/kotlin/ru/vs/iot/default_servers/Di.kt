package ru.vs.iot.default_servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.default_servers.repository.DefaultServersRepository
import ru.vs.iot.default_servers.repository.DefaultServersRepositoryImpl
import ru.vs.iot.di.Modules

fun Modules.featureDefaultServers() = DI.Module("feature-default-servers") {
    bindSingleton<DefaultServersRepository> { DefaultServersRepositoryImpl() }
}