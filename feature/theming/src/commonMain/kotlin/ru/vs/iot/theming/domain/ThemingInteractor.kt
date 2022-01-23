package ru.vs.iot.theming.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal interface ThemingInteractor {
    fun observeCurrentTheme(): Flow<Theme>
}

internal class ThemingInteractorImpl : ThemingInteractor {
    override fun observeCurrentTheme(): Flow<Theme> {
        return flowOf(Theme.SYSTEM)
    }
}
