package ru.vs.iot.server.domain.about

import ru.vs.iot.dto.server.AboutServerDTO
import ru.vs.iot.server.BuildConfig

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

