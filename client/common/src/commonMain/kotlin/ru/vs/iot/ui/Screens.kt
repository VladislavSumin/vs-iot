@file:Suppress("MatchingDeclarationName", "Filename")

package ru.vs.iot.ui

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen
import ru.vs.iot.ui.main.MainScreenView

@Parcelize
internal object MainScreen : Screen {
    @Composable
    override fun ScreenView() {
        MainScreenView()
    }
}
