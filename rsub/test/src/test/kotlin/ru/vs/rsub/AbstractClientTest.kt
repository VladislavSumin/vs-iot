package ru.vs.rsub

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class AbstractClientTest {
    @Test
    fun `check connection states`() = runBlockingTest {
        val connection = mock<RSubConnection> {
            on { receive } doReturn flow { while (true) delay(1000) }
        }

        val connector = mock<RSubConnector> {
            onBlocking { connect() } doReturn connection
        }
        val client = RSubClientAbstract(connector)

        client.observeConnectionStatus().collect {
            println("AAAA")
        }
        println("bbbbb")
    }
}
