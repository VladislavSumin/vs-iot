package ru.vs.iot.settings.ui

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen

@Parcelize
object SettingsScreen : Screen {
    @Composable
    override fun ScreenView() {
        SettingsScreenView()
    }
}
