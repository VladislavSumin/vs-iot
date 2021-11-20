package ru.vs.iot.ui.core.navigation

import androidx.navigation.NavHostController

class Navigator(private val controller: NavHostController) {
    fun popBackStack() = controller.popBackStack()
    fun navigate(screen: Screen) = controller.navigate(screen.route)
}
