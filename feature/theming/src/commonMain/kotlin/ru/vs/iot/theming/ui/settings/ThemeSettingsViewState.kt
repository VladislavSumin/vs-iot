package ru.vs.iot.theming.ui.settings

import dev.icerock.moko.resources.StringResource
import ru.vs.iot.theming.domain.Theme

internal data class ThemeSettingsViewState(
    val currentTheme: Theme,
    val themes: List<ThemeModel>
) {
    data class ThemeModel(val theme: Theme, val themeName: StringResource)
}
