package ru.vs.iot.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindProvider
import org.kodein.di.instanceOrNull

internal class DiViewModelFactory(
    private val injector: DirectDI,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return injector.instanceOrNull<ViewModel>(modelClass.name) as T? ?: modelClass.newInstance()
    }
}

inline fun <reified T : ViewModel> DI.Builder.bindViewModel(
    overrides: Boolean? = null,
    noinline creator: DirectDI.() -> T
) = bindProvider(T::class.java.name, overrides, creator)
