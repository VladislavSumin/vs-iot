package ru.vs.iot.di

import org.kodein.di.DI
import ru.vs.core.di.Modules
import ru.vs.iot.entities.featureEntities
import ru.vs.iot.ktor_client.coreKtorClient
import ru.vs.core.serialization.coreSerialization
import ru.vs.iot.servers.featureServers
import ru.vs.iot.services.featureServices
import ru.vs.iot.settings.coreSettings
import ru.vs.iot.theming.featureTheming

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreSettings())
    importOnce(Modules.coreKtorClient())
    importOnce(Modules.coreSerialization())

    importOnce(Modules.clientCommonDatabase())

    importOnce(Modules.featureServers())
    importOnce(Modules.featureEntities())
    importOnce(Modules.featureServices())
    importOnce(Modules.featureTheming())
}
