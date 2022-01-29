package ru.vs.iot.di

import org.kodein.di.DI
import ru.vs.iot.ktor_client.coreKtorClient
import ru.vs.iot.servers.featureServers
import ru.vs.iot.settings.coreSettings
import ru.vs.iot.theming.featureTheming

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreSettings())
    importOnce(Modules.coreKtorClient())

    importOnce(Modules.clientCommonDatabase())

    importOnce(Modules.featureServers())
    importOnce(Modules.featureTheming())
}
