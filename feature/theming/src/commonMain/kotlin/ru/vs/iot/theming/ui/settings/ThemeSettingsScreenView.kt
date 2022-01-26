package ru.vs.iot.theming.ui.settings

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
import dev.icerock.moko.resources.compose.stringResource
import ru.vs.iot.decompose.view_model.decomposeViewModel
import ru.vs.iot.theming.MR
import ru.vs.iot.theming.domain.Theme

@Composable
fun ThemeSettingsScreenView() {
    val viewModel = decomposeViewModel<ThemeSettingsViewModel>()
    val theme = viewModel.theme.collectAsState(Theme.SYSTEM).value

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp, 8.dp)) {
            Text(stringResource(MR.strings.theming_settings_screen_title))
            Text(theme.toString())
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.Green,
            ) {
                Row {
                    TextButton(onClick = { viewModel.onClickSetTheme(Theme.LIGHT) }) {
                        Text(stringResource(MR.strings.theming_settings_screen_theme_light))
                    }
                    TextButton(onClick = { viewModel.onClickSetTheme(Theme.SYSTEM) }) {
                        Text(stringResource(MR.strings.theming_settings_screen_theme_system))
                    }
                    TextButton(onClick = { viewModel.onClickSetTheme(Theme.DARK) }) {
                        Text(stringResource(MR.strings.theming_settings_screen_theme_dark))
                    }
                }
            }
        }
    }
}