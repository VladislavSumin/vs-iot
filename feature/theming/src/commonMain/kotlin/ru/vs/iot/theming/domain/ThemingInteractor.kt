package ru.vs.iot.theming.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vs.iot.theming.repository.ThemingRepository

internal interface ThemingInteractor {
    fun observeCurrentTheme(): StateFlow<Theme>
    suspend fun setTheme(theme: Theme)
}

internal class ThemingInteractorImpl(private val repository: ThemingRepository) : ThemingInteractor {
    private val theme = MutableStateFlow(repository.theme)

    override fun observeCurrentTheme(): StateFlow<Theme> = theme

    override suspend fun setTheme(theme: Theme) {
        repository.theme = theme
        this.theme.emit(theme)
    }
}
