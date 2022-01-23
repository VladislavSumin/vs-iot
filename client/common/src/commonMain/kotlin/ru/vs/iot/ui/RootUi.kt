package ru.vs.iot.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfade
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen
import ru.vs.iot.navigation.ScreenFactory

@Composable
fun RootUi(componentContext: ComponentContext) {
    val router = remember {
        componentContext.router<Screen, Screen>(
            initialConfiguration = S1,
            key = "root",
            childFactory = ::createChild
        )
    }
    val routerState = remember { router.state }

    CompositionLocalProvider(LocalNavigation provides router) {
        RootNavigation {
            NavigationScreen(routerState)
        }
    }
}

@Suppress("TooGenericExceptionThrown")
val LocalNavigation = staticCompositionLocalOf<Router<Screen, Screen>> {
    throw RuntimeException("Local navigation is not set")
}

@Suppress("UnusedPrivateMember")
private fun createChild(config: Screen, componentContext: ComponentContext) = config

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

@Composable
private fun NavigationScreen(
    routerState: Value<RouterState<Screen, Screen>>,
) {
    Children(routerState, animation = crossfade()) {
        when (val child = it.instance) {
            is S1 -> S1F.render(child)
            is S2 -> S2F.render(child)
        }
    }
}

@Composable
private fun Screen1() {
    val router = LocalNavigation.current
    Text("Hello from Screen1")
    Button(onClick = { router.push(S2) }) {
        Text("goto s2")
    }
}

@Composable
private fun Screen2() {
    val router = LocalNavigation.current
    Text("Hello from Screen2")
    Button(onClick = { router.pop() }) {
        Text("back")
    }
}

private object S1F : ScreenFactory<S1> {
    @Composable
    override fun render(screen: S1) {
        Screen1()
    }
}

private object S2F : ScreenFactory<S2> {
    @Composable
    override fun render(screen: S2) {
        Screen2()
    }
}

@Parcelize
private object S1 : Screen

@Parcelize
private object S2 : Screen
