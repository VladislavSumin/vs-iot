package ru.vs.iot.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

interface HttpClientFactory {
    fun createHttpClient(): HttpClient
}

class HttpClientFactoryImpl(
    private val json: Json
) : HttpClientFactory {
    override fun createHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }
    }
}
