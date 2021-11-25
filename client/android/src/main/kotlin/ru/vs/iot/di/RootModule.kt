package ru.vs.iot.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.iot.api.HttpClientFactory
import ru.vs.iot.api.HttpClientFactoryImpl
import ru.vs.iot.repository.DatabaseFactory
import ru.vs.iot.repository.DatabaseFactoryImpl

fun Modules.root() = DI.Module("root") {
    // Api
    bindSingleton<HttpClientFactory> { HttpClientFactoryImpl() }
    bindSingleton { i<HttpClientFactory>().createHttpClient() }

    // Database
    bindSingleton<DatabaseFactory> { DatabaseFactoryImpl(i()) }
    bindSingleton { instance<DatabaseFactory>().createDatabase() }
}
