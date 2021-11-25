package ru.vs.iot.settings

import org.kodein.di.DI
import org.kodein.di.inSet
import org.kodein.di.provider
import ru.vs.iot.di.Modules
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.navigation.ui.destination.createDestination
import ru.vs.iot.settings.ui.Settings
import ru.vs.iot.settings.ui.SettingsScreen

fun Modules.featureSettings() = DI.Module("feature-settings") {
    inSet<NavigationDestination> { provider { Screen.Settings.createDestination { SettingsScreen() } } }
}
