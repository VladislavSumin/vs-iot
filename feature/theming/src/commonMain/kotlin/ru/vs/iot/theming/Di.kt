package ru.vs.iot.theming

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.theming.domain.ThemingInteractor
import ru.vs.iot.theming.domain.ThemingInteractorImpl
import ru.vs.iot.theming.ui.selector.ThemeSelectorViewModel
import ru.vs.iot.theming.ui.settings.ThemeSettingsViewModel

fun Modules.featureTheming() = DI.Module("feature-theming") {
    // Interactors
    bindSingleton<ThemingInteractor> { ThemingInteractorImpl() }

    // ViewModels
    bindProvider { ThemeSettingsViewModel(i()) }
    bindProvider { ThemeSelectorViewModel(i()) }
}
