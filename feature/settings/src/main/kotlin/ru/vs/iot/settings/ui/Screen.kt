package ru.vs.iot.settings.ui

import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

val Screen.Settings
    get() = NoArgNavigationCommand("settings")
