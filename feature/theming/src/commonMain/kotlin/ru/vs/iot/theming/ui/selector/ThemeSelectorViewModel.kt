package ru.vs.iot.theming.ui.selector

import ru.vs.iot.decompose.view_model.ViewModel
import ru.vs.iot.theming.domain.ThemingInteractor

internal class ThemeSelectorViewModel(
    private val themingInteractor: ThemingInteractor
) : ViewModel() {
    val theme = themingInteractor.observeCurrentTheme()
}
