package ru.vs.iot.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.*
import ru.vs.iot.di.bindViewModel
import ru.vs.iot.domain.ServersInteractor
import ru.vs.iot.domain.ServersInteractorImpl
import ru.vs.iot.ui.screen.servers.ServersViewModel

val rootModule = DI.Module("root") {
    bindSingleton<ServersInteractor> { ServersInteractorImpl() }

    bindSingleton<ViewModelProvider.Factory> { DiViewModelFactory(directDI) }
    bindViewModel { ServersViewModel(instance()) }
}