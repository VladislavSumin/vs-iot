package ru.vs.iot.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.iot.navigation.ui.command.NavigationCommand
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.utils.forEachApply

@Composable
fun NavigationHost(startDestination: NavigationCommand) {
    val navigator = LocalNavigation.current
    val di = localDI()
    val destinations = remember { di.direct.instance<Set<NavigationDestination>>() }
    NavHost(navigator.navController, startDestination = startDestination.route) {
        destinations.forEachApply { setup() }
    }
}
