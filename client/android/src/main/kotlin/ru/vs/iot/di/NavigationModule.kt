package ru.vs.iot.di

import org.kodein.di.DI
import org.kodein.di.inSet
import org.kodein.di.provider
import ru.vs.iot.navigation.coreNavigation
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.navigation.ui.destination.createDestination
import ru.vs.iot.ui.core.navigation.Entities
import ru.vs.iot.ui.core.navigation.Settings
import ru.vs.iot.ui.screen.entities.EntititesScreen
import ru.vs.iot.ui.screen.settings.SettingsScreen

fun Modules.navigation() = DI.Module("navigation") {
    importOnce(Modules.coreNavigation())
    inSet<NavigationDestination> { provider { Screen.Entities.createDestination { EntititesScreen() } } }
    inSet<NavigationDestination> { provider { Screen.Settings.createDestination { SettingsScreen() } } }
}
