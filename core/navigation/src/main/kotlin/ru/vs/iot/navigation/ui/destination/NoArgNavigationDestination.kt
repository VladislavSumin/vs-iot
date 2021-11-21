package ru.vs.iot.navigation.ui.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

class NoArgNavigationDestination(
    private val navigationCommand: NoArgNavigationCommand,
    private val content: @Composable (NavBackStackEntry) -> Unit
) : NavigationDestination {
    override fun NavGraphBuilder.setup() = composable(navigationCommand.route, content = content)
}

fun NoArgNavigationCommand.createDestination(content: @Composable (NavBackStackEntry) -> Unit) =
    NoArgNavigationDestination(this, content)
