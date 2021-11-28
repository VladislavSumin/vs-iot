package ru.vs.iot.serialization

import kotlinx.serialization.json.Json

interface JsonFactory {
    fun createDefault(): Json
}

internal class JsonFactoryImpl : JsonFactory {
    override fun createDefault() = Json {
        encodeDefaults = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        allowStructuredMapKeys = true
        prettyPrint = true
        useArrayPolymorphism = true
    }
}
