package ru.vs.iot.serialization

import kotlinx.serialization.modules.SerializersModule
import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i

fun Modules.coreSerialization() = DI.Module("core-serialization") {
    bindSet<SerializersModule>()

    bindSingleton<JsonFactory> { JsonFactoryImpl(i()) }
    bindSingleton { i<JsonFactory>().createDefault() }
}
