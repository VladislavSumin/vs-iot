package ru.vs.iot.default_servers.domain

import ru.vs.iot.default_servers.repository.DefaultServersRepository
import ru.vs.iot.default_servers.repository.DefaultServersSettingsRepository

interface DefaultServersInteractor {
}

internal class DefaultServersInteractorImpl(
    private val defaultServersRepository: DefaultServersRepository,
    private val defaultServersSettingsRepository: DefaultServersSettingsRepository,
) : DefaultServersInteractor {

}