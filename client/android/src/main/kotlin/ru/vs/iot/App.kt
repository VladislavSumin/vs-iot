package ru.vs.iot

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.AllInstances
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.allInstances
import org.kodein.di.android.x.androidXModule
import ru.vs.iot.default_servers.featureDefaultServers
import ru.vs.iot.di.Modules
import ru.vs.iot.di.root
import kotlin.coroutines.EmptyCoroutineContext

class App : Application(), DIAware {
    private val applicationScope = CoroutineScope(EmptyCoroutineContext)

    override val di: DI by DI.lazy {
        import(androidXModule(this@App))

        importOnce(Modules.root())

        importOnce(Modules.featureDefaultServers())
    }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            val autoStartComponents: List<AutoStartComponent> by allInstances()
            autoStartComponents.size
            autoStartComponents.forEach {
                launch { it.start() }
            }
        }
    }
}
