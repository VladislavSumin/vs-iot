package ru.vs.iot.ui.core.bottom_bar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.vs.iot.entities.ui.Entities
import ru.vs.iot.navigation.ui.LocalNavigation
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.command.NavigationCommand
import ru.vs.iot.services.ui.Services

@Composable
fun BottomNavigationBar() {
    val navigator = LocalNavigation.current
    val navController = navigator.navController
    val navBackStackEntry by navigator.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.navigationCommand.route,
                icon = { Icon(item.icon, "") },
                onClick = {
                    navController.navigate(item.navigationCommand.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

private val items = listOf(Item.Entities, Item.Services)

private sealed class Item(
    val navigationCommand: NavigationCommand,
    val icon: ImageVector
) {
    // TODO поискать и добавить адекватные иконки
    object Entities : Item(Screen.Entities, Icons.Filled.Home)
    object Services : Item(Screen.Services, Icons.Filled.Email)
}
