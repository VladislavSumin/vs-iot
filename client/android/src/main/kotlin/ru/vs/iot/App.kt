package ru.vs.iot

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import ru.vs.iot.di.rootModule

class App : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(androidXModule(this@App))
        importOnce(rootModule)
    }
}
