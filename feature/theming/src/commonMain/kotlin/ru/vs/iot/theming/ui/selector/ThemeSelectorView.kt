package ru.vs.iot.theming.ui.selector

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import ru.vs.core.decompose.view_model.decomposeViewModel
import ru.vs.core.uikit.theme.MainTheme
import ru.vs.iot.theming.domain.Theme

@Composable
fun ThemeSelectorView(content: @Composable () -> Unit) {
    val viewModel = decomposeViewModel<ThemeSelectorViewModel>()
    val isDark = when (viewModel.theme.collectAsState(Theme.SYSTEM).value) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.DARK -> true
        Theme.LIGHT -> false
    }
    MainTheme(darkTheme = isDark) {
        Surface(Modifier.fillMaxSize(), content = content)
    }
}
