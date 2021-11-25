package ru.vs.iot.server.domain.about

import ru.vs.iot.server.BuildConfig
import ru.vs.iot.servers.dto.AboutServerDTO

interface AboutServerInteractor {
    suspend fun getVersion(): String
    suspend fun getAbout(): AboutServerDTO
}

class AboutServerInteractorImpl : AboutServerInteractor {
    override suspend fun getVersion(): String = BuildConfig.version
    override suspend fun getAbout() = AboutServerDTO(
        version = getVersion()
    )
}
