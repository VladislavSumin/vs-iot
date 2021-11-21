package ru.vs.iot.navigation.ui.destination

import androidx.navigation.NavGraphBuilder

interface NavigationDestination {
    fun NavGraphBuilder.setup()
}
