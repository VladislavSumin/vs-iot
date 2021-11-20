package ru.vs.iot.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.rememberNavController

@Suppress("TooGenericExceptionThrown")
val LocalNavigation = staticCompositionLocalOf<Navigator> {
    throw RuntimeException("Local navigation is not set")
}

@Composable
fun NavigationHolder(content: @Composable () -> Unit) {
    val navController = rememberNavController()
    val navigator = Navigator(navController)
    CompositionLocalProvider(LocalNavigation provides navigator, content = content)
}
