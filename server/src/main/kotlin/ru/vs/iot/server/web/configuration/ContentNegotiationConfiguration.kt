package ru.vs.iot.server.web.configuration

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

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