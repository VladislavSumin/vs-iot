package ru.vs.iot.di

import org.kodein.di.DI
import ru.vs.iot.theming.featureTheming

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.featureTheming())
}
