package ru.vs.iot.ui.core.navigation

sealed class Screen(val route: String) {
    object Servers : Screen("servers")
    object AddServer : Screen("add_server")
    object Settings : Screen("settings")
}
