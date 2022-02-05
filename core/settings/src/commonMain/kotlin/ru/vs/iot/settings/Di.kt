package ru.vs.iot.settings

import com.russhwolf.settings.Settings
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules

fun Modules.coreSettings() = DI.Module("core-settings") {
    bindSingleton<Settings.Factory> { createSettingsFactory() }
}

internal expect fun DirectDI.createSettingsFactory(): Settings.Factory
