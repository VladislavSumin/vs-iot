package ru.vs.iot.server.web.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
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
