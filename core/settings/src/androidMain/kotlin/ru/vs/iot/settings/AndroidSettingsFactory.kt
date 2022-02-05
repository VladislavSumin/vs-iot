package ru.vs.iot.settings

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import org.kodein.di.DirectDI
import ru.vs.core.di.i

internal actual fun DirectDI.createSettingsFactory(): Settings.Factory {
    return AndroidSettings.Factory(i())
}
