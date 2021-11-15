package ru.vs.iot.server.web.configuration

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json

interface ContentNegotiationConfiguration {
    fun Application.configure()
}

class ContentNegotiationConfigurationImpl : ContentNegotiationConfiguration {
    override fun Application.configure() {
        install(ContentNegotiation) {
            // TODO добавить prettyPrint в дебаг режиме
            json()
        }
    }
}
