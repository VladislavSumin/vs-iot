package ru.vs.iot.di

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.inSet
import org.kodein.di.provider
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.navigation.ui.destination.createDestination
import ru.vs.iot.ui.core.navigation.AddServer
import ru.vs.iot.ui.core.navigation.Servers
import ru.vs.iot.ui.core.navigation.Settings
import ru.vs.iot.ui.screen.add_server.AddServerScreen
import ru.vs.iot.ui.screen.servers.ServersScreen
import ru.vs.iot.ui.screen.settings.SettingsScreen

fun Modules.navigation() = DI.Module("navigation") {
    bindSet<NavigationDestination>()
    inSet<NavigationDestination> { provider { Screen.Servers.createDestination { ServersScreen() } } }
    inSet<NavigationDestination> { provider { Screen.AddServer.createDestination { AddServerScreen() } } }
    inSet<NavigationDestination> { provider { Screen.Settings.createDestination { SettingsScreen() } } }
}
