package ru.vs.iot.theming.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vs.iot.decompose.view_model.decomposeViewModel
import ru.vs.iot.theming.domain.Theme

@Composable
fun ThemeSettingsScreenView() {
    val viewModel = decomposeViewModel<ThemeSettingsViewModel>()
    val theme = viewModel.theme.collectAsState(Theme.SYSTEM).value

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp, 8.dp)) {
            Text("Theme")
            Text(theme.toString())
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.Green,
            ) {
                Row {
                    TextButton(onClick = { viewModel.onClickSetTheme(Theme.LIGHT) }) { Text("Светлая") }
                    TextButton(onClick = { viewModel.onClickSetTheme(Theme.SYSTEM) }) { Text("Системная") }
                    TextButton(onClick = { viewModel.onClickSetTheme(Theme.DARK) }) { Text("Темная") }
                }
            }
        }
    }
}
