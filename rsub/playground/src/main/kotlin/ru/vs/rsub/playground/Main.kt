package ru.vs.rsub.playground

import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector

fun main() {
    val connector = object : RSubConnector {
        override suspend fun connect(): RSubConnection {
            TODO("Not yet implemented")
        }
    }
    TestClientImpl(connector)
}
