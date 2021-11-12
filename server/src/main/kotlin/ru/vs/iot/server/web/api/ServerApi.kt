package ru.vs.iot.server.web.api

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

class ServerApi {
    fun Routing.bind() {
        getVersion()
    }

    private fun Routing.getVersion() = get("/api/server/version") {
        this.call.respondText("3.3.3")
    }
}