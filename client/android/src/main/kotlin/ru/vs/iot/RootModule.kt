package ru.vs.iot

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.domain.ServersInteractorImpl

val rootModule = DI.Module("root") {
    bindSingleton<ServersInteractor> { ServersInteractorImpl() }
}