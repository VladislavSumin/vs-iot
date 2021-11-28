package ru.vs.iot.navigation

import org.kodein.di.DI
import org.kodein.di.bindSet
import ru.vs.iot.di.Modules
import ru.vs.iot.navigation.ui.destination.NavigationDestination

fun Modules.coreNavigation() = DI.Module("core-navigation") {
    bindSet<NavigationDestination>()
}
