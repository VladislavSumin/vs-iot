package ru.vs.iot.servers.ui.add_server

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.channels.consumeEach
import ru.vs.iot.compose.di.kodeinViewModel
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

    var serverName by remember { mutableStateOf("") }
    var serverUrl by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(value = serverName, onValueChange = { serverName = it }, label = { Text("Server Name") })
        OutlinedTextField(value = serverUrl, onValueChange = { serverUrl = it }, label = { Text("Server Url") })
        Button(onClick = { viewModel.onClickSave(serverName, serverUrl) }) { Text("Save") }
    }
}
