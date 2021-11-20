package ru.vs.iot.ui.core.navigation

import androidx.navigation.NavHostController

class Navigator(val navController: NavHostController) {
    fun popBackStack() = navController.popBackStack()
    fun navigate(screen: Screen) = navController.navigate(screen.route)
}
