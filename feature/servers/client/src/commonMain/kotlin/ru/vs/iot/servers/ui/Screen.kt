package ru.vs.iot.servers.ui

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.iot.navigation.Screen
import ru.vs.iot.servers.ui.add_server.AddServerScreenView
import ru.vs.iot.servers.ui.server.ServersScreenView

@Parcelize
object ServersScreen : Screen {
    @Composable
    override fun ScreenView() {
        ServersScreenView()
    }
}

@Parcelize
object AddServerScreen : Screen {
    @Composable
    override fun ScreenView() {
        AddServerScreenView()
    }
}
