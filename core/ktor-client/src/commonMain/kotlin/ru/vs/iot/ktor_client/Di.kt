package ru.vs.iot.ktor_client

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.ktor_client.api.HttpClientFactory
import ru.vs.iot.ktor_client.api.createHttpClientFactory

fun Modules.coreKtorClient() = DI.Module("core-ktor-client") {
    bindSingleton { createHttpClientFactory() }
    bindSingleton { i<HttpClientFactory>().createHttpClient() }
}
