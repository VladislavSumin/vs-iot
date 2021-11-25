package ru.vs.iot.entities.ui

import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NoArgNavigationCommand

val Screen.Entities
    get() = NoArgNavigationCommand("entities")
