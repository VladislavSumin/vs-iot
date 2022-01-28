package ru.vs.iot.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.api.HttpClientFactory
import ru.vs.iot.api.HttpClientFactoryImpl

fun Modules.root() = DI.Module("root") {
    // Api
    bindSingleton<HttpClientFactory> { HttpClientFactoryImpl(i()) }
    bindSingleton { i<HttpClientFactory>().createHttpClient() }
}
