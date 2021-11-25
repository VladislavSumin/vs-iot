package ru.vs.iot.di

import org.kodein.di.DI
import ru.vs.iot.navigation.coreNavigation

fun Modules.navigation() = DI.Module("navigation") {
    importOnce(Modules.coreNavigation())
}
