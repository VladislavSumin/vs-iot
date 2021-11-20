package ru.vs.iot.ui.core.navigation

import ru.vs.iot.navigation.ui.NavigationCommand

sealed class Screen(override val route: String) : NavigationCommand {
    object Servers : Screen("servers")
    object AddServer : Screen("add_server")
    object Settings : Screen("settings")
}
