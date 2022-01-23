package ru.vs.iot.navigation.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfade

@Composable
fun NavigationView() {
    val routerState = LocalNavigation2.current.state
    Children(
        routerState = routerState,
        animation = crossfade(),
    ) { childCreated -> childCreated.instance.ScreenView() }
}
