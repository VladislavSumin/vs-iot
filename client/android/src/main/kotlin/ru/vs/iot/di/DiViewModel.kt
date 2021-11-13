package ru.vs.iot.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import org.kodein.di.*
import org.kodein.di.compose.localDI

class DiViewModelFactory(
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

@Composable
inline fun <reified VM : ViewModel> kodeinViewModel(
    key: String? = null
): VM {
    val di = localDI()
    return viewModel(
        VM::class.java,
        LocalViewModelStoreOwner.current!!,
        key,
        di.direct.instance()
    )
}