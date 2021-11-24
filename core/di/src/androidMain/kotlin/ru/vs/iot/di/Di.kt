package ru.vs.iot.di

import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun Modules.coreDi() = DI.Module("core-di") {
    bindSingleton<ViewModelProvider.Factory> { DiViewModelFactory(directDI) }
}
