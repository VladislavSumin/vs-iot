package ru.vs.iot.ui.screen.add_server

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.consumeEach
import ru.vs.iot.di.kodeinViewModel
import ru.vs.iot.navigation.ui.LocalNavigation

@Composable
fun AddServerScreen(
    viewModel: AddServerViewModel = kodeinViewModel()
) {
    val navigation = LocalNavigation.current
    LaunchedEffect(Unit) {
        viewModel.events.consumeEach {
            when (it) {
                Event.CloseScreen -> navigation.popBackStack()
            }
        }
    }

    val (serverName, setServerName) = remember { mutableStateOf("") }
    val (serverUrl, setServerUrl) = remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            value = serverName,
            onValueChange = { setServerName(it) },
            label = { Text("Server Name") }
        )
        OutlinedTextField(
            value = serverUrl,
            onValueChange = { setServerUrl(it) },
            label = { Text("Server Url") }
        )
        Button(
            onClick = { viewModel.onClickSave(serverName, serverUrl) }
        ) {
            Text("Save")
        }
    }
}
