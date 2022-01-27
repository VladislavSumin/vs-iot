package ru.vs.iot.theming.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.string
import ru.vs.iot.theming.domain.Theme

internal interface ThemingRepository {
    var theme: Theme
}

internal class ThemingRepositoryImpl(settingsFactory: Settings.Factory) : ThemingRepository {
    private val settings = settingsFactory.create("feature_theming")

    private var themeName: String by settings.string("theme", Theme.SYSTEM.name)

    override var theme: Theme
        get() = Theme.valueOf(themeName)
        set(value) {
            themeName = value.name
        }
}
