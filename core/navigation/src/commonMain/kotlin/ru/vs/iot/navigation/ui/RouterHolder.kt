package ru.vs.iot.navigation.ui

import com.arkivanov.decompose.router.Router
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.iot.navigation.Screen

internal class RouterHolder(val router: Router<Screen, ScreenInstance>) : InstanceKeeper.Instance {
    override fun onDestroy() {
        // no op
    }
}

fun InstanceKeeper.getOrCreateRouter(
    routerName: String,
    factory: () -> Router<Screen, ScreenInstance>
): Router<Screen, ScreenInstance> {
    return getOrCreate(key = RouterHolder::class.qualifiedName + "-" + routerName) { RouterHolder(factory()) }.router
}
