package ru.vs.iot.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inSet
import org.kodein.di.singleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.iot.entities.serialization.EntitiesStatesSerializerFactory
import ru.vs.iot.entities.serialization.EntitiesStatesSerializerFactoryImpl

fun Modules.featureEntitiesCore() = DI.Module("feature-entities-core") {
    bindSingleton<EntitiesStatesSerializerFactory> { EntitiesStatesSerializerFactoryImpl() }

    inSet { singleton { i<EntitiesStatesSerializerFactory>().createSerializer() } }
}
