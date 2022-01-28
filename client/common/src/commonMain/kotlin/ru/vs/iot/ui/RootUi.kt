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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.decompose.ui.LocalComponentContextHolder
import ru.vs.iot.navigation.Screen
import ru.vs.iot.navigation.ui.LocalNavigation2
import ru.vs.iot.navigation.ui.LocalNavigationHolder
import ru.vs.iot.navigation.ui.NavigationView
import ru.vs.iot.navigation.ui.defaultRouter
import ru.vs.iot.servers.ui.ServersScreen
import ru.vs.iot.settings.ui.SettingsScreen
import ru.vs.iot.theming.ui.selector.ThemeSelectorView

@Composable
fun RootUi(componentContext: ComponentContext) {
    val router = remember {
        componentContext.defaultRouter(S1)
    }

    LocalComponentContextHolder(componentContext) {
        ThemeSelectorView {
            LocalNavigationHolder(router) {
                BottomBarView {
                    NavigationView()
                }
            }
        }
    }
}

@Composable
private fun BottomBarView(content: @Composable BoxScope.() -> Unit) {
    val router = LocalNavigation2.current
    val currentScreen = router.state.subscribeAsState().value.activeChild.instance
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                    BottomNavigationItem(
                        selected = currentScreen::class == S1::class,
                        icon = { Icon(Icons.Filled.Home, "") },
                        onClick = { router.navigate { listOf(S1) } }
                    )
                    BottomNavigationItem(
                        selected = currentScreen::class == ServersScreen::class,
                        icon = { Icon(Icons.Filled.Face, "") },
                        onClick = { router.navigate { listOf(ServersScreen) } }
                    )
                    BottomNavigationItem(
                        selected = currentScreen::class == SettingsScreen::class,
                        icon = { Icon(Icons.Filled.Settings, "") },
                        onClick = { router.navigate { listOf(SettingsScreen) } }
                    )
                }
            },
        ) {
            Box(modifier = Modifier.padding(it), content = content)
        }
    }
}

@Composable
private fun Screen1() {
    val router = LocalNavigation2.current
    Text("Hello from Screen1")
    Button(onClick = { router.push(S2) }) {
        Text("goto s2")
    }
}

@Composable
private fun Screen2() {
    val router = LocalNavigation2.current
    Text("Hello from Screen2")
    Button(onClick = { router.pop() }) {
        Text("back")
    }
}

@Parcelize
private object S1 : Screen {
    @Composable
    override fun ScreenView() {
        Screen1()
    }
}

@Parcelize
private object S2 : Screen {
    @Composable
    override fun ScreenView() {
        Screen2()
    }
}
