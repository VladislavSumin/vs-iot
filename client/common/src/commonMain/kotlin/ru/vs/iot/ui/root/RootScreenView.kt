package ru.vs.iot.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ui.LocalComponentContextHolder
import ru.vs.core.navigation.ui.LocalRootNavigationHolder
import ru.vs.core.navigation.ui.NavigationContentView
import ru.vs.core.navigation.ui.defaultRouter
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
