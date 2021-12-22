package ru.vs.rsub.client

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
open class BaseClientTest {

    lateinit var sendToClientChannel: SendChannel<String>

    lateinit var receiveFromClientChannel: ReceiveChannel<String>

    @Mock
    lateinit var connection: RSubConnection

    @Mock
    lateinit var connector: RSubConnector

    lateinit var scope: CoroutineScope

    internal lateinit var client: TestClientImpl

    @BeforeEach
    fun beforeEach(): Unit = runBlocking {
        val sendChannel = Channel<String>()
        sendToClientChannel = sendChannel

        val receiveChannel = Channel<String>()
        receiveFromClientChannel = receiveChannel

        reset(connection)
        whenever(connection.receive) doReturn sendChannel.receiveAsFlow()
        whenever(connection.send(any())).doSuspendableAnswer { receiveChannel.send(it.arguments[0] as String) }

        reset(connector)
        whenever(connector.connect()) doReturn connection

        scope = CoroutineScope(CoroutineName("test-scope"))

        client = TestClientImpl(connector, scope = scope, connectionKeepAliveTime = 0, reconnectInterval = 0)
    }

    @AfterEach
    fun afterEach(): Unit = runBlocking {
        scope.cancel()
        scope.coroutineContext.job.join()

        sendToClientChannel.close()
    }
}
