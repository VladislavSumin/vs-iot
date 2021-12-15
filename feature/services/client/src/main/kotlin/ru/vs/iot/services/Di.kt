package ru.vs.iot.services

import org.kodein.di.DI
import org.kodein.di.inSet
import org.kodein.di.provider
import ru.vs.iot.di.Modules
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.navigation.ui.destination.createDestination
import ru.vs.iot.services.ui.Services
import ru.vs.iot.services.ui.ServicesScreen

fun Modules.featureServices() = DI.Module("feature-services") {
    // Navigation
    inSet<NavigationDestination> { provider { Screen.Services.createDestination { ServicesScreen() } } }
}
