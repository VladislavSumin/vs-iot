package ru.vs.iot.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import ru.vs.iot.decompose.ui.LocalComponentContextHolder
import ru.vs.iot.navigation.ui.LocalRootNavigationHolder
import ru.vs.iot.navigation.ui.NavigationContentView
import ru.vs.iot.navigation.ui.defaultRouter
import ru.vs.iot.theming.ui.selector.ThemeSelectorView
import ru.vs.iot.ui.MainScreen

@Composable
fun RootScreenView(componentContext: ComponentContext) {
    val router = remember {
        componentContext.defaultRouter(MainScreen, "rootRouter")
    }

    LocalComponentContextHolder(componentContext) {
        ThemeSelectorView {
            LocalRootNavigationHolder(router) {
                NavigationContentView()
            }
        }
    }
}
