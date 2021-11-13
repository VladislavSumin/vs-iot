package ru.vs.iot.ui.core

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ru.vs.iot.ui.screen.add_server.AddServerScreen
import ru.vs.iot.ui.screen.servers.ServersScreen

val LocalNavigation = staticCompositionLocalOf<NavController> {
    throw RuntimeException()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreen() {
    val navController = rememberAnimatedNavController()
    CompositionLocalProvider(
        LocalNavigation provides navController
    ) {
        AnimatedNavHost(navController, startDestination = "server") {
            composable("server") { ServersScreen() }
            composable("add-server") { AddServerScreen() }
        }
    }
}