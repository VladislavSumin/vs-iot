package ru.vs.iot.navigation.ui

import androidx.navigation.NavHostController

class Navigator(val navController: NavHostController) {
    fun popBackStack() = navController.popBackStack()
    fun navigate(navigationCommand: NavigationCommand) = navController.navigate(navigationCommand.route)
}
