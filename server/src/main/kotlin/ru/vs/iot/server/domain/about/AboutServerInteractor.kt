package ru.vs.iot.server.domain.about

import ru.vs.iot.server.BuildConfig

interface AboutServerInteractor {
    suspend fun getVersion(): String
}

class AboutServerInteractorImpl : AboutServerInteractor {
    override suspend fun getVersion(): String = BuildConfig.version
}
