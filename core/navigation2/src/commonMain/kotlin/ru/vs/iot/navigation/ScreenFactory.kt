package ru.vs.iot.navigation

import androidx.compose.runtime.Composable

interface ScreenFactory<S : Screen> {
    @Composable
    fun render(screen: S)
}
