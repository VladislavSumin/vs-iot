package ru.vs.iot.theming.ui.selector

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.vs.iot.decompose.view_model.decomposeViewModel
import ru.vs.iot.theming.domain.Theme
import ru.vs.iot.uikit.theme.MainTheme

@Composable
fun ThemeSelectorView(content: @Composable () -> Unit) {
    val viewModel = decomposeViewModel<ThemeSelectorViewModel>()
    val isDark = when (viewModel.theme.collectAsState(Theme.SYSTEM).value) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.DARK -> true
        Theme.LIGHT -> false
    }
    MainTheme(darkTheme = isDark, content = content)
}
