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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen
import ru.vs.iot.navigation.ui.LocalNavigation2
import ru.vs.iot.navigation.ui.LocalNavigationHolder
import ru.vs.iot.navigation.ui.NavigationView
import ru.vs.iot.navigation.ui.defaultRouter

@Composable
fun RootUi(componentContext: ComponentContext) {
    val router = remember {
        componentContext.defaultRouter(S1)
    }

    LocalNavigationHolder(router) {
        RootNavigation {
            NavigationView()
        }
    }
}

@Composable
private fun RootNavigation(content: @Composable BoxScope.() -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                    BottomNavigationItem(
                        selected = false,
                        icon = { Icon(Icons.Filled.Home, "") },
                        onClick = {}
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
    override fun render() {
        Screen1()
    }
}

@Parcelize
private object S2 : Screen {
    @Composable
    override fun render() {
        Screen2()
    }
}
