package ru.vs.iot.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.decompose.router.Router
import ru.vs.iot.navigation.Screen

@Suppress("TooGenericExceptionThrown")
val LocalRootNavigation = staticCompositionLocalOf<Router<Screen, ScreenInstance>> {
    throw RuntimeException("Local root navigation is not set")
}

@Composable
fun LocalRootNavigationHolder(router: Router<Screen, ScreenInstance>, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalRootNavigation provides router) {
        CompositionLocalProvider(LocalNavigation provides router, content = content)
    }
}
