package ru.vs.iot.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vs.iot.ui.screen.add_server.AddServerScreen
import ru.vs.iot.ui.screen.servers.ServersScreen

val LocalNavigation = staticCompositionLocalOf<NavController> {
    throw RuntimeException()
}

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavigation provides navController
    ) {
        NavHost(navController, startDestination = "server") {
            composable("server") { ServersScreen() }
            composable("add-server") { AddServerScreen() }
        }
    }
}