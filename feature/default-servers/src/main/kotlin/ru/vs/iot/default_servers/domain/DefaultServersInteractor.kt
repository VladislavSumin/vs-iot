package ru.vs.iot.default_servers.domain

import ru.vs.iot.default_servers.repository.DefaultServer
import ru.vs.iot.default_servers.repository.DefaultServersRepository
import ru.vs.iot.default_servers.repository.DefaultServersSettingsRepository

interface DefaultServersInteractor {
    suspend fun initializeDefaultServersIfNeeded(block: suspend (List<DefaultServer>) -> Unit)
}

internal class DefaultServersInteractorImpl(
    private val defaultServersRepository: DefaultServersRepository,
    private val defaultServersSettingsRepository: DefaultServersSettingsRepository,
) : DefaultServersInteractor {
    override suspend fun initializeDefaultServersIfNeeded(block: suspend (List<DefaultServer>) -> Unit) {
        if (defaultServersSettingsRepository.isDefaultServersInitialized()) return
        block(defaultServersRepository.getDefaultServers())
        defaultServersSettingsRepository.setDefaultServersInitialized()
    }
}