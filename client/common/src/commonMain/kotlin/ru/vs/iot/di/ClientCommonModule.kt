package ru.vs.iot.di

import org.kodein.di.DI
import ru.vs.iot.settings.coreSettings
import ru.vs.iot.theming.featureTheming

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreSettings())

    importOnce(Modules.featureTheming())
}
