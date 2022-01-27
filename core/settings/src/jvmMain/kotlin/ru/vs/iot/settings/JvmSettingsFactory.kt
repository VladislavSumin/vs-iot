package ru.vs.iot.settings

import com.russhwolf.settings.JvmPreferencesSettings
import com.russhwolf.settings.Settings
import org.kodein.di.DirectDI

internal actual fun DirectDI.createSettingsFactory(): Settings.Factory {
    return JvmPreferencesSettings.Factory()
}
