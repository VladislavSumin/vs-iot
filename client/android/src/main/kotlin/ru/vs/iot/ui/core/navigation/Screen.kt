package ru.vs.iot.ui.core.navigation

import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

val Screen.Entities
    get() = NoArgNavigationCommand("entities")
