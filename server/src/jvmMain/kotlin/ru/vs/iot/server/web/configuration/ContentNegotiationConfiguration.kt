package ru.vs.iot.server.web.configuration

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import kotlinx.serialization.json.Json

interface ContentNegotiationConfiguration {
    fun Application.configure()
}

class ContentNegotiationConfigurationImpl(
    private val json: Json
) : ContentNegotiationConfiguration {
    override fun Application.configure() {
        install(ContentNegotiation) {
            json(json)
        }
    }
}
