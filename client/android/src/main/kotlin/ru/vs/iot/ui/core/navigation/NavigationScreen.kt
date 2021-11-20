package ru.vs.iot.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vs.iot.ui.screen.add_server.AddServerScreen
import ru.vs.iot.ui.screen.servers.ServersScreen
import ru.vs.iot.ui.screen.settings.SettingsScreen

@Suppress("TooGenericExceptionThrown")
val LocalNavigation = staticCompositionLocalOf<Navigator> {
    throw RuntimeException("Local navigation is not set")
}

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val navigator = Navigator(navController)
    CompositionLocalProvider(
        LocalNavigation provides navigator
    ) {
        NavHost(navController, startDestination = Screen.Servers.route) {
            composable(Screen.Servers.route) { ServersScreen() }
            composable(Screen.AddServer.route) { AddServerScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
