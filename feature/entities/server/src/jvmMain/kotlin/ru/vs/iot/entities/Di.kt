package ru.vs.iot.entities

import org.kodein.di.DI
import ru.vs.iot.di.Modules

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesCore())
}
