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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.iot.decompose.ui.LocalComponentContext
import ru.vs.iot.theming.domain.ThemingInteractorImpl

@Composable
fun ThemeSettingsScreenView() {
    val componentContext = LocalComponentContext.current
    val viewModel = componentContext.instanceKeeper.getOrCreate(ThemeSettingsViewModel::class) { ThemeSettingsViewModel(ThemingInteractorImpl()) }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp, 8.dp)) {
            Text("Theme")
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.Green,
            ) {
                Row {
                    TextButton(onClick = {}) { Text("Светлая") }
                    TextButton(onClick = {}) { Text("Системная") }
                    TextButton(onClick = {}) { Text("Темная") }
                }
            }
        }
    }
}
