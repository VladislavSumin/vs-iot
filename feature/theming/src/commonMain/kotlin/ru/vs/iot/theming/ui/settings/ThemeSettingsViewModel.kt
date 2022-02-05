package ru.vs.iot.theming.ui.settings

import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.core.decompose.view_model.ViewModel
import ru.vs.iot.theming.MR
import ru.vs.iot.theming.domain.Theme
import ru.vs.iot.theming.domain.ThemingInteractor

internal class ThemeSettingsViewModel(
    private val themingInteractor: ThemingInteractor
) : ViewModel() {
    private val themes = Theme.values().map { ThemeSettingsViewState.ThemeModel(it, it.nameRes) }

    val state = themingInteractor.observeCurrentTheme().map {
        ThemeSettingsViewState(
            currentTheme = it,
            themes = themes,
        )
    }

    fun onClickSetTheme(theme: Theme) {
        viewModelScope.launch {
            themingInteractor.setTheme(theme)
        }
    }
}

private val Theme.nameRes: StringResource
    get() = when (this) {
        Theme.SYSTEM -> MR.strings.theming_settings_screen_theme_system
        Theme.DARK -> MR.strings.theming_settings_screen_theme_dark
        Theme.LIGHT -> MR.strings.theming_settings_screen_theme_light
    }
