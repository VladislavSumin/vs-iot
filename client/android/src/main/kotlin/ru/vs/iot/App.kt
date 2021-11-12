package ru.vs.iot

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware

class App : Application(), DIAware {
    override val di: DI by DI.lazy { }
}