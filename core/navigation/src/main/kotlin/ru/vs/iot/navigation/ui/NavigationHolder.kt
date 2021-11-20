package ru.vs.iot.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.rememberNavController

@Suppress("TooGenericExceptionThrown")
val LocalNavigation = staticCompositionLocalOf<Navigator> {
    throw RuntimeException("Local navigation is not set")
}

@Composable
fun NavigationHolder(content: @Composable () -> Unit) {
    val navController = rememberNavController()
    val navigator = remember { Navigator(navController) }
    CompositionLocalProvider(LocalNavigation provides navigator, content = content)
}
