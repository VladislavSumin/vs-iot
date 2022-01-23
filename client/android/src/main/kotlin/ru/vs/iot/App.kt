package ru.vs.iot

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.allInstances
import org.kodein.di.android.x.androidXModule
import ru.vs.iot.autostart.AutoStartComponent
import ru.vs.iot.di.Modules
import ru.vs.iot.di.clientCommon
import ru.vs.iot.di.coreDi
import ru.vs.iot.di.navigation
import ru.vs.iot.di.root
import ru.vs.iot.entities.featureEntities
import ru.vs.iot.serialization.coreSerialization
import ru.vs.iot.servers.featureServers
import ru.vs.iot.services.featureServices
import kotlin.coroutines.EmptyCoroutineContext

class App : Application(), DIAware {
    private val applicationScope = CoroutineScope(EmptyCoroutineContext)

    override val di: DI by DI.lazy {
        import(androidXModule(this@App))

        importOnce(Modules.clientCommon())

        importOnce(Modules.coreDi())
        importOnce(Modules.coreSerialization())

        importOnce(Modules.root())
        importOnce(Modules.navigation())

        importOnce(Modules.featureEntities())
        importOnce(Modules.featureServers())
        importOnce(Modules.featureServices())
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
