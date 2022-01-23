package ru.vs.iot.navigation.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.router
import ru.vs.iot.navigation.Screen

fun ComponentContext.defaultRouter(initialScreen: Screen): Router<Screen, Screen> {
    return this.router(
        initialConfiguration = initialScreen,
        key = "defaultRouter",
        childFactory = ::createChild
    )
}

@Suppress("UnusedPrivateMember")
private fun createChild(config: Screen, componentContext: ComponentContext) = config
