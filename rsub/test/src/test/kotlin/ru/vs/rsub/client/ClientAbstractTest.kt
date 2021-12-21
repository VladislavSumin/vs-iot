package ru.vs.rsub.client

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.vs.rsub.RSubClientAbstract
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.RSubConnector

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class ClientAbstractTest {
    @Mock
    lateinit var connection: RSubConnection

    @Mock
    lateinit var connector: RSubConnector

    lateinit var scope: CoroutineScope

    lateinit var client: RSubClientAbstract

    @BeforeEach
    fun beforeEach(): Unit = runBlocking {
        reset(connection)
        whenever(connection.receive) doReturn flow { delay(Long.MAX_VALUE) }

        reset(connector)
        whenever(connector.connect()) doReturn connection

        scope = CoroutineScope(CoroutineName("test-scope"))

        client = RSubClientAbstract(connector, scope = scope, connectionKeepAliveTime = 0, reconnectInterval = 0)
    }

    @AfterEach
    fun afterEach(): Unit = runBlocking {
        scope.cancel()
        scope.coroutineContext.job.join()
    }

    @Test
    fun `normal connect and then disconnect`(): Unit = runBlocking {
        client.observeConnectionStatus().test {
            assertEquals(RSubConnectionStatus.CONNECTING, awaitItem())
            assertEquals(RSubConnectionStatus.CONNECTED, awaitItem())
            cancel()
        }

        scope.cancel()
        scope.coroutineContext.job.join()

        verify(connector, times(1)).connect()
        verify(connection, times(1)).receive
        verify(connection, times(0)).send(any())
        verify(connection, times(1)).close()
    }
}
