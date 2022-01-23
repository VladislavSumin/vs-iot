package ru.vs.iot.theming.ui.settings

import kotlinx.coroutines.launch
import ru.vs.iot.decompose.view_model.ViewModel
import ru.vs.iot.theming.domain.Theme
import ru.vs.iot.theming.domain.ThemingInteractor

internal class ThemeSettingsViewModel(
    private val themingInteractor: ThemingInteractor
) : ViewModel() {
    val theme = themingInteractor.observeCurrentTheme()

    fun onClickSetTheme(theme: Theme) {
        viewModelScope.launch {
            themingInteractor.setTheme(theme)
        }
    }
}
