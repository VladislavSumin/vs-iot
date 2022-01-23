package ru.vs.iot.theming.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal interface ThemingInteractor {
    fun observeCurrentTheme(): StateFlow<Theme>
    suspend fun setTheme(theme: Theme)
}

internal class ThemingInteractorImpl : ThemingInteractor {
    private val theme = MutableStateFlow(Theme.SYSTEM)
    override fun observeCurrentTheme(): StateFlow<Theme> = theme
    override suspend fun setTheme(theme: Theme) {
        this.theme.emit(theme)
    }
}
