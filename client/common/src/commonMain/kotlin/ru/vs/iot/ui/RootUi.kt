package ru.vs.iot.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RootUi() {
    RootNavigation {
        Text("Hello from decompose")
    }
}

private val SHOW_DRAWER_NAVIGATION_STATE = 600.dp
private val SHOW_DRAWER_NAVIGATION_MOVE_CONTENT_STATE = 800.dp

private enum class NavigationState {
    BOTTOM,
    DRAWER,
    DRAWER_MOVE_CONTENT
}

@Composable
private fun RootNavigation(content: @Composable BoxScope.() -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val width = this.maxWidth
        val navigationState = when {
            width < SHOW_DRAWER_NAVIGATION_STATE -> NavigationState.BOTTOM
            width < SHOW_DRAWER_NAVIGATION_MOVE_CONTENT_STATE -> NavigationState.DRAWER
            else -> NavigationState.DRAWER_MOVE_CONTENT
        }

        Scaffold(
            bottomBar = {
                if (navigationState == NavigationState.BOTTOM)
                    BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                        BottomNavigationItem(
                            selected = false,
                            icon = { Icon(Icons.Filled.Home, "") },
                            onClick = {}
                        )
                    }
            },
            drawerContent = if (navigationState != NavigationState.BOTTOM) {
                { Text("Hello") }
            } else null
        ) {
            Box(modifier = Modifier.padding(it), content = content)
        }
    }
}
