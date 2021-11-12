package ru.vs.iot.server.domain.about

import kotlinx.serialization.Serializable
import ru.vs.iot.server.BuildConfig

interface AboutServerInteractor {
    suspend fun getVersion(): String
    suspend fun getAbout(): AboutServer
}

class AboutServerInteractorImpl : AboutServerInteractor {
    override suspend fun getVersion(): String = BuildConfig.version
    override suspend fun getAbout() = AboutServer(
        version = getVersion()
    )
}

@Serializable
data class AboutServer constructor(
    val version: String
)
