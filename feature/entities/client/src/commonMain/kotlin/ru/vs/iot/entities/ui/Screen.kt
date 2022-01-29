@file:Suppress("MatchingDeclarationName", "Filename")

package ru.vs.iot.entities.ui

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen

@Parcelize
object EntitiesScreen : Screen {
    @Composable
    override fun ScreenView() {
        EntitiesScreenView()
    }
}
