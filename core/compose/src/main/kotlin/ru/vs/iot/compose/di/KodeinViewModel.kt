package ru.vs.iot.compose.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.di.instance

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
