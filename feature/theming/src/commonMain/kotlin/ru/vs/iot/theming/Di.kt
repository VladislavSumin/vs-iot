package ru.vs.iot.theming

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.iot.theming.domain.ThemingInteractor
import ru.vs.iot.theming.domain.ThemingInteractorImpl
import ru.vs.iot.theming.repository.ThemingRepository
import ru.vs.iot.theming.repository.ThemingRepositoryImpl
import ru.vs.iot.theming.ui.selector.ThemeSelectorViewModel
import ru.vs.iot.theming.ui.settings.ThemeSettingsViewModel

fun Modules.featureTheming() = DI.Module("feature-theming") {
    // Repositories
    bindSingleton<ThemingRepository> { ThemingRepositoryImpl(i()) }

    // Interactors
    bindSingleton<ThemingInteractor> { ThemingInteractorImpl(i()) }

    // ViewModels
    bindProvider { ThemeSettingsViewModel(i()) }
    bindProvider { ThemeSelectorViewModel(i()) }
}
