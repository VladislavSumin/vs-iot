package ru.vs.iot.ui.core.navigation

import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

val Screen.Servers
    get() = NoArgNavigationCommand("servers")

val Screen.AddServer
    get() = NoArgNavigationCommand("add_server")

val Screen.Settings
    get() = NoArgNavigationCommand("settings")
