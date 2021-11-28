package ru.vs.iot.serialization

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i

fun Modules.coreSerialization() = DI.Module("core-serialization") {
    bindSingleton<JsonFactory> { JsonFactoryImpl() }
    bindSingleton { i<JsonFactory>().createDefault() }
}
