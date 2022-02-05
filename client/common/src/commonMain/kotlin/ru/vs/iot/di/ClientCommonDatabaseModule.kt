package ru.vs.iot.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.core.di.Modules
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.repository.createDatabaseFactory

internal fun Modules.clientCommonDatabase() = DI.Module("client-common-database") {
    bindSingleton { createDatabaseFactory() }
    bindSingleton { instance<DatabaseFactory>().createDatabase() }
}
