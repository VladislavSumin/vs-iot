package ru.vs.iot

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import ru.vs.iot.default_servers.featureDefaultServers
import ru.vs.iot.di.Modules
import ru.vs.iot.di.root

class App : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(androidXModule(this@App))

        importOnce(Modules.root())

        importOnce(Modules.featureDefaultServers())
    }
}
