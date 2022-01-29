package ru.vs.iot.navigation.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import ru.vs.iot.decompose.ui.LocalComponentContextHolder
import ru.vs.iot.navigation.Screen

class ScreenInstance(private val screen: Screen, private val componentContext: ComponentContext) {
    @Composable
    internal fun ScreenInstanceView() {
        LocalComponentContextHolder(componentContext) {
            screen.ScreenView()
        }
    }
}
