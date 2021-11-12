package ru.vs.iot.server.web.api

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.vs.iot.server.domain.about.AboutServerInteractor

class ServerApi(
    private val aboutServerInteractor: AboutServerInteractor
) {
    fun Routing.bind() {
        getVersion()
        getAbout()
    }

    private fun Routing.getVersion() = get("/api/server/version") {
        this.call.respondText { aboutServerInteractor.getVersion() }
    }

    private fun Routing.getAbout() = get("/api/server/about") {
        val about = aboutServerInteractor.getAbout()
        this.call.respond(about)
    }
}