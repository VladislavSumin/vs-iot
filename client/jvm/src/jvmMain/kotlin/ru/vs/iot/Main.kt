package ru.vs.iot

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.kodein.di.DI
import org.kodein.di.compose.withDI
import ru.vs.iot.di.Modules
import ru.vs.iot.di.clientCommon
import ru.vs.iot.logging.setupDefault
import ru.vs.iot.ui.RootUi

fun main() {
    Logger.setupDefault()

    val di = DI.lazy {
        importOnce(Modules.clientCommon())
    }

    val lifecycle = LifecycleRegistry()
    val defaultComponentContext = DefaultComponentContext(lifecycle)

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = this::exitApplication,
            state = windowState,
            title = "vs-iot"
        ) {
            withDI(di) {
                DesktopScrollbarStyle {
                    RootUi(defaultComponentContext)
                }
            }
        }
    }
}

@Composable
private fun DesktopScrollbarStyle(
    scrollbarStyle: ScrollbarStyle = defaultScrollbarStyle(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalScrollbarStyle provides scrollbarStyle, content = content)
}
