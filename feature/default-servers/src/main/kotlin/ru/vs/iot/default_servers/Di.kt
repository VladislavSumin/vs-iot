package ru.vs.iot.default_servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.iot.default_servers.domain.DefaultServersInteractor
import ru.vs.iot.default_servers.domain.DefaultServersInteractorImpl
import ru.vs.iot.default_servers.repository.DefaultServersRepository
import ru.vs.iot.default_servers.repository.DefaultServersRepositoryImpl
import ru.vs.iot.default_servers.repository.DefaultServersSettingsRepository
import ru.vs.iot.default_servers.repository.DefaultServersSettingsRepositoryImpl

fun Modules.featureDefaultServers() = DI.Module("feature-default-servers") {
    bindSingleton<DefaultServersRepository> { DefaultServersRepositoryImpl() }
    bindSingleton<DefaultServersSettingsRepository> { DefaultServersSettingsRepositoryImpl(i()) }

    bindSingleton<DefaultServersInteractor> { DefaultServersInteractorImpl(i(), i()) }
}
