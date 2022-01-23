package ru.vs.iot.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.iot.theming.ui.settings.ThemeSettingsScreenView

@Composable
internal fun SettingsScreenView() {
    Column(Modifier.padding(16.dp, 24.dp)) {
        ThemeSettingsScreenView()
    }
}
