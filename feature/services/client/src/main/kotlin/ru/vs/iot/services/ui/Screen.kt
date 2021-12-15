package ru.vs.iot.services.ui

import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

val Screen.Services
    get() = NoArgNavigationCommand("services")
