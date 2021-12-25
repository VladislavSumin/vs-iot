package ru.vs.rsub.server

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
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
import ru.vs.rsub.RSubServer
import ru.vs.rsub.TestInterfaceImpl

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
open class BaseServerTest {
    protected val testInterface = TestInterfaceImpl()
    private val testServerSubscriptions = TestServerSubscriptionsImpl(testInterface)

    lateinit var sendToServerChannel: SendChannel<String>
    lateinit var receiveFromServerChannel: ReceiveChannel<String>

    @Mock
    lateinit var connection: RSubConnection

    private lateinit var scope: CoroutineScope
    private lateinit var server: RSubServer

    @BeforeEach
    fun beforeEach(): Unit = runBlocking {
        scope = CoroutineScope(CoroutineName("test-scope"))
        server = RSubServer(testServerSubscriptions)

        val sendChannel = Channel<String>()
        sendToServerChannel = sendChannel

        // TODO вынести в общий код тестов
        val receiveChannel = Channel<String>()
        receiveFromServerChannel = receiveChannel
        reset(connection)
        whenever(connection.receive) doReturn sendChannel.receiveAsFlow()
        whenever(connection.send(any())).doSuspendableAnswer { receiveChannel.send(it.arguments[0] as String) }
    }

    @AfterEach
    fun afterEach(): Unit = runBlocking {
        scope.cancel()
        scope.coroutineContext.job.join()
        sendToServerChannel.close()
    }

    fun initConnection() {
        scope.launch { server.handleNewConnection(connection) }
    }
}
