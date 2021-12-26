package ru.vs.rsub

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KType
import kotlin.reflect.typeOf

open class RSubClientAbstract(
    private val connector: RSubConnector,

    private val reconnectInterval: Long = 3000,
    connectionKeepAliveTime: Long = 6000,

    private val json: Json = Json,
    scope: CoroutineScope = GlobalScope,
    private val logger: Logger = Logger.withTag("RSubClient"),
) {
    /**
     * Contains next id, using to create new subscription with uncial id
     */
    private val nextId = AtomicInteger(0)

    /**
     * This shared flow keeps the connection open and automatically reconnects in case of errors.
     * The connection will be maintained as long as there are active subscriptions
     */
    @Suppress("TooGenericExceptionCaught")
    private val connection: Flow<ConnectionState> = channelFlow {
        logger.d("Start observe connection")

        var connectionGlobal: RSubConnection? = null
        try {
            while (true) {
                try {
                    // this cope prevents reconnect before old connection receive flow not closed
                    // see crateConnectedState
                    coroutineScope {
                        send(ConnectionState.Connecting)
                        val connection = connector.connect()
                        connectionGlobal = connection
                        val state = crateConnectedState(connection, this)
                        send(state)
                    }
                } catch (e: Exception) {
                    when (e) {
                        is RSubExpectedExceptionOnConnectionException -> {
                            logger.d("Connection or listening failed with checked exception: ${e.message}")
                            send(ConnectionState.Disconnected)
                            connectionGlobal?.close()
                            delay(reconnectInterval)
                            logger.d("Reconnecting...")
                        }
                        is CancellationException -> throw e
                        else -> {
                            val message = "Unknown exception on connection or listening"
                            val exception = RSubException(message, e)
                            logger.e(message, exception)
                            throw exception
                        }
                    }
                }
            }
        } finally {
            logger.d("Stopping observe connection")
            withContext(NonCancellable) {
                connectionGlobal?.close()
                logger.d("Stop observe connection")
            }
        }
    }
        .distinctUntilChanged()
        .onEach { logger.d("New connection status: ${it.status}") }
        .shareIn(
            scope + CoroutineName("RSubClient::connection"),
            SharingStarted.WhileSubscribed(
                stopTimeoutMillis = connectionKeepAliveTime,
                replayExpirationMillis = 0
            ),
            1
        )

    /**
     * Keep connection active while subscribed, return actual connection status
     */
    fun observeConnectionStatus(): Flow<RSubConnectionStatus> = connection.map { it.status }

    protected suspend inline fun <reified T : Any> processSuspend(
        interfaceName: String,
        methodName: String,
    ): T = processSuspend(interfaceName, methodName, typeOf<T>())

    @Suppress("TooGenericExceptionCaught")
    protected suspend fun <T : Any> processSuspend(
        interfaceName: String,
        methodName: String,
        type: KType
    ): T {
        return withConnection { connection ->
            val id = nextId.getAndIncrement()
            try {
                coroutineScope {
                    val responseDeferred = async { connection.incoming.filter { it.id == id }.first() }
                    connection.subscribe(id, interfaceName, methodName)
                    val response = responseDeferred.await()
                    parseServerMessage<T>(response, type)
                }
            } catch (e: Exception) {
                // withContext(NonCancellable) {
                //    // TODO кажется тут только при отмене корутины нужно отписываться
                //    connection.unsubscribe(id)
                // }
                throw RSubException("Unknown exception", e)
            }
        }
    }

//    private fun processFlow(
//        name: String,
//        method: KFunction<*>,
//        arguments: Array<Any?>?
//    ): Flow<Any?> = channelFlow {
//        //Check reconnect policy
//        val throwException = when (
//            method.findAnnotation<RSubFlowPolicy>()?.policy ?: RSubFlowPolicy.Policy.THROW_EXCEPTION
//        ) {
//            RSubFlowPolicy.Policy.THROW_EXCEPTION -> true
//            RSubFlowPolicy.Policy.SUPPRESS_EXCEPTION_AND_RECONNECT -> false
//        }
//
//        withConnection(throwException) { connection ->
//            val id = nextId.getAndIncrement()
//            try {
//                coroutineScope {
//                    launch {
//                        connection.incoming
//                            .filter { it.id == id }
//                            .collect {
//                                val item = parseServerMessage(
//                                    it,
//                                    method.returnType.arguments[0].type!!
//                                )
//                                send(item)
//                            }
//                    }
//                    connection.subscribe(id, name, method, arguments)
//                }
//            } catch (e: FlowCompleted) {
//                // suppress
//            } catch (e: Exception) {
//                withContext(NonCancellable) {
//                    connection.unsubscribe(id)
//                }
//                throw e
//            }
//        }
//    }

    /**
     * Create wrapped connection, with shared receive flow
     * all received messages reply to that flow, flow haven`t buffer!
     *
     * @param connection raw connection from connector
     * @param scope coroutine scope of current connection session
     */
    private fun crateConnectedState(connection: RSubConnection, scope: CoroutineScope): ConnectionState.Connected {
        return ConnectionState.Connected(
            { connection.send(json.encodeToString(it)) },
            connection.receive
                .map { json.decodeFromString<RSubMessage>(it) }
                // Hot observable, subscribe immediately, shared, no buffer, connection scoped
                .shareIn(scope, SharingStarted.Eagerly)
        )
    }

    /**
     * Try to subscribe to [connection], wait connected state and execute given block with [ConnectionState.Connected]
     * If connection failed throw exception
     *
     * @param throwOnDisconnect - if false then suppress network exception form socket and [block],
     * and then if false retry to call [block]
     */
    @Suppress("TooGenericExceptionThrown")
    private suspend fun <T> withConnection(
        throwOnDisconnect: Boolean = true,
        block: suspend (connection: ConnectionState.Connected) -> T
    ): T {
        return connection.filter {
            when (it) {
                is ConnectionState.Connecting -> false
                is ConnectionState.Connected -> true
                is ConnectionState.Disconnected ->
                    // TODO добавить кастомные ошибки
                    if (throwOnDisconnect) throw RuntimeException("Connection in state DISCONNECTED")
                    else false
            }
        }
            .map { it as ConnectionState.Connected }
            // Hack, use map to prevent closing connection.
            // Connection subscription active all time while block executing.
            .mapLatest(block)
            .retry {
                when {
                    // TODO unexpected coroutine behavior, check if fixed on new version
//                    it is CancellationException -> {
//                        log.warn("Connection was canceled by previous connection")
//                        true
//                    }
                    throwOnDisconnect -> false
//                    it is SocketException -> true
                    else -> {
                        logger.e("Unexpected exception ", it)
                        false
                    }
                }
            }
            .first()
    }

    @Suppress("ThrowsCount", "TooGenericExceptionThrown")
    private fun <T : Any> parseServerMessage(message: RSubMessage, type: KType): T = when (message) {
        is RSubMessage.Data -> {
            val data = message.data
            if (data != null) json.decodeFromJsonElement(json.serializersModule.serializer(type), data) as T
            else Unit as T
        }
        is RSubMessage.FlowComplete -> throw FlowCompleted()
        is RSubMessage.Error -> throw RuntimeException("Server return error")
        is RSubMessage.Subscribe, is RSubMessage.Unsubscribe -> throw RuntimeException("Unexpected server data")
    }

    private suspend fun ConnectionState.Connected.subscribe(
        id: Int,
        name: String,
        methodName: String,
        // arguments: Array<Any?>?
    ) = send(RSubMessage.Subscribe(id, name, methodName))

    // private suspend fun ConnectionState.Connected.unsubscribe(id: Int) = send(RSubMessage.Unsubscribe(id))

    private sealed class ConnectionState(val status: RSubConnectionStatus) {
        object Connecting : ConnectionState(RSubConnectionStatus.CONNECTING)
        class Connected(
            val send: suspend (message: RSubMessage) -> Unit,
            val incoming: Flow<RSubMessage>
        ) : ConnectionState(RSubConnectionStatus.CONNECTED)

        object Disconnected : ConnectionState(RSubConnectionStatus.DISCONNECTED)
    }

    private class FlowCompleted : Exception()
}
