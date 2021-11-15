package ru.vs.iot.ui.screen.add_server

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.vs.iot.di.kodeinViewModel

@Composable
fun AddServerScreen(
    viewModel: AddServerViewModel = kodeinViewModel()
) {
    Text("Add server screen")
}
