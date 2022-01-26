package ru.vs.iot.theming.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import ru.vs.iot.decompose.view_model.decomposeViewModel
import ru.vs.iot.theming.MR
import ru.vs.iot.theming.domain.Theme

private const val PERCENT_50 = 50

@Composable
fun ThemeSettingsScreenView() {
    val viewModel = decomposeViewModel<ThemeSettingsViewModel>()
    val state = viewModel.state.collectAsState(null).value ?: return

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp, 8.dp)) {
            Text(
                style = MaterialTheme.typography.subtitle1,
                text = stringResource(MR.strings.theming_settings_screen_title)
            )
            ThemeSelector(state, viewModel::onClickSetTheme)
        }
    }
}

@Composable
private fun ThemeSelector(state: ThemeSettingsViewState, onSelectTheme: (Theme) -> Unit) {
    val selectedTabIndex = state.themes.indexOfFirst { it.theme == state.currentTheme }

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        FancyIndicator(Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]))
    }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            divider = {},
            indicator = indicator,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .clip(RoundedCornerShape(PERCENT_50))
                .border(BorderStroke(1.dp, MaterialTheme.colors.primaryVariant), RoundedCornerShape(PERCENT_50))
        ) {
            state.themes.forEachIndexed { index, theme ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onSelectTheme(theme.theme) }
                ) {
                    Text(modifier = Modifier.padding(8.dp, 4.dp), text = stringResource(theme.themeName))
                }
            }
        }
    }
}

@Composable
private fun FancyIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(2.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, MaterialTheme.colors.primary), RoundedCornerShape(PERCENT_50))
    )
}
