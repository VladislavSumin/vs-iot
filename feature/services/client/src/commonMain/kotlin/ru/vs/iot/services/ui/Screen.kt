@file:Suppress("MatchingDeclarationName", "Filename")

package ru.vs.iot.services.ui

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen

@Parcelize
object ServicesScreen : Screen {
    @Composable
    override fun ScreenView() {
        ServicesScreenView()
    }
}
