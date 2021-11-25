package ru.vs.iot.servers.ui

import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

val Screen.Servers
    get() = NoArgNavigationCommand("servers")

val Screen.AddServer
    get() = NoArgNavigationCommand("add_server")
