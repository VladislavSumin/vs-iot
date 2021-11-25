package ru.vs.iot.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.iot.entities.ui.Entities
import ru.vs.iot.navigation.ui.NavigationHolder
import ru.vs.iot.navigation.ui.NavigationHost
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.ui.core.bottom_bar.BottomNavigationBar

@Composable
fun MainScreen() {
    NavigationHolder {
        Scaffold(bottomBar = { BottomNavigationBar() }) {
            Box(Modifier.padding(0.dp, it.calculateTopPadding(), 0.dp, it.calculateBottomPadding())) {
                NavigationHost(Screen.Entities)
            }
        }
    }
}
