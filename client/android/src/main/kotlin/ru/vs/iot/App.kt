package ru.vs.iot

import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.allInstances
import org.kodein.di.android.x.androidXModule
import ru.vs.core.di.Modules
import ru.vs.iot.autostart.AutoStartComponent
import ru.vs.iot.di.clientCommon
import kotlin.coroutines.EmptyCoroutineContext

class App : Application(), DIAware {
    private val applicationScope = CoroutineScope(EmptyCoroutineContext)

    override val di: DI by DI.lazy {
        import(androidXModule(this@App))

        importOnce(Modules.clientCommon())
    }

    override fun onCreate() {
        super.onCreate()

        Logger.setLogWriters(platformLogWriter())

        applicationScope.launch {
            val autoStartComponents: List<AutoStartComponent> by allInstances()
            autoStartComponents.size
            autoStartComponents.forEach {
                launch { it.start() }
            }
        }
    }
}
