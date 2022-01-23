package ru.vs.iot.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.decompose.router.Router
import ru.vs.iot.navigation.Screen

@Suppress("TooGenericExceptionThrown")
val LocalNavigation2 = staticCompositionLocalOf<Router<Screen, ScreenInstance>> {
    throw RuntimeException("Local navigation is not set")
}

@Composable
fun LocalNavigationHolder(router: Router<Screen, ScreenInstance>, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalNavigation2 provides router, content = content)
}
