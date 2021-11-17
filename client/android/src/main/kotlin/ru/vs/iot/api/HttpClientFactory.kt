package ru.vs.iot.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature

interface HttpClientFactory {
    fun createHttpClient(): HttpClient
}

class HttpClientFactoryImpl : HttpClientFactory {
    override fun createHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature)
        }
    }
}