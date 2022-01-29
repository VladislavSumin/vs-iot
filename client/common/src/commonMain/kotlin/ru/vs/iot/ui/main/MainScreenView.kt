package ru.vs.iot.ui.main

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.decompose.ui.LocalComponentContext
import ru.vs.iot.entities.ui.EntitiesScreen
import ru.vs.iot.navigation.Screen
import ru.vs.iot.navigation.ui.LocalNavigation
import ru.vs.iot.navigation.ui.LocalNavigationHolder
import ru.vs.iot.navigation.ui.LocalRootNavigation
import ru.vs.iot.navigation.ui.NavigationContentView
import ru.vs.iot.navigation.ui.defaultRouter
import ru.vs.iot.navigation.ui.getOrCreateRouter
import ru.vs.iot.servers.ui.ServersScreen
import ru.vs.iot.services.ui.ServicesScreen
import ru.vs.iot.settings.ui.SettingsScreen
import kotlin.reflect.KClass

@Composable
internal fun MainScreenView() {
    val componentContext = LocalComponentContext.current
    val router = componentContext.instanceKeeper.getOrCreateRouter("mainScreenRouter") {
        componentContext.defaultRouter(S1, "mainScreenRouter")
    }
    LocalNavigationHolder(router) {
        BottomBarView {
            NavigationContentView()
        }
    }
}

@Composable
private fun BottomBarView(content: @Composable BoxScope.() -> Unit) {
    val router = LocalNavigation.current
    val currentScreen = router.state.subscribeAsState().value.activeChild.configuration
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                    TestEnum.values().forEach { navItem ->
                        BottomNavigationItem(
                            selected = currentScreen::class == navItem.screen,
                            icon = { Icon(navItem.icon, null) },
                            label = { Text(navItem.title) },
                            onClick = { router.navigate { listOf(navItem.screenFactory()) } }
                        )
                    }
                }
            },
        ) {
            Box(modifier = Modifier.padding(it), content = content)
        }
    }
}

enum class TestEnum : NavigationItem {
    Home {
        override val title = "Home"
        override val icon = Icons.Filled.Home
        override val screen = S1::class
        override val screenFactory = { S1 }
    },
    Entities {
        override val title = "Entities"
        override val icon = Icons.Filled.Email
        override val screen = EntitiesScreen::class
        override val screenFactory = { EntitiesScreen }
    },
    Services {
        override val title = "Services"
        override val icon = Icons.Filled.DateRange
        override val screen = ServicesScreen::class
        override val screenFactory = { ServicesScreen }
    },
    Servers {
        override val title = "Servers"
        override val icon = Icons.Filled.Face
        override val screen = ServersScreen::class
        override val screenFactory = { ServersScreen }
    },
    Settings {
        override val title = "Settings"
        override val icon = Icons.Filled.Email
        override val screen = SettingsScreen::class
        override val screenFactory = { SettingsScreen }
    },
}

interface NavigationItem {
    val title: String
    val icon: ImageVector
    val screen: KClass<out Screen>
    val screenFactory: () -> Screen
}

@Composable
private fun Screen1() {
    val router = LocalRootNavigation.current
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

@Parcelize
object S1 : Screen {
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
