package ru.vs.iot.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vs.iot.navigation.ui.LocalNavigation
import ru.vs.iot.ui.screen.add_server.AddServerScreen
import ru.vs.iot.ui.screen.servers.ServersScreen
import ru.vs.iot.ui.screen.settings.SettingsScreen

@Composable
fun NavigationHost() {
    val navigator = LocalNavigation.current
    NavHost(navigator.navController, startDestination = Screen.Servers.route) {
        composable(Screen.Servers.route) { ServersScreen() }
        composable(Screen.AddServer.route) { AddServerScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}
