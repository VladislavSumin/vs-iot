package ru.vs.rsub

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.SocketException
import java.net.SocketTimeoutException

open class RSubClientAbstract(
    private val connector: RSubConnector,

    private val reconnectInterval: Long = 3000,
    connectionKeepAliveTime: Long = 6000,

    private val json: Json,
    scope: CoroutineScope,
    private val logger: Logger = Logger.withTag("RSubClient"),
) {

    /**
     * This shared flow keeps the connection open and automatically reconnects in case of errors.
     * The connection will be maintained as long as there are active subscriptions
     */
    @Suppress("TooGenericExceptionCaught")
    private val connection: Flow<ConnectionState> = channelFlow {
        logger.d("Start observe connection")
        send(ConnectionState.Connecting)

        var connectionGlobal: RSubConnection? = null
        try {
            while (true) {
                try {
                    val connection = connector.connect()
                    connectionGlobal = connection
                    val state = crateConnectedState(connection, this)
                    send(state)
                } catch (e: Exception) {
                    when (e) {
                        is SocketTimeoutException,
                        is SocketException -> {
                            logger.d("Connection failed by socket exception: ${e.message}")
                            send(ConnectionState.Disconnected)
                            connectionGlobal?.close()
                            delay(reconnectInterval)
                            logger.d("Reconnecting...")
                        }
                        is CancellationException -> throw e
                        else -> {
                            logger.e("Unknown exception on connection", e)
                            throw e
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
            scope,
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

    protected suspend fun <T> processSuspend(
        interfaceName: String,
        methodName: String,
    ): T {
        return TODO()
    }

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

    private sealed class ConnectionState(val status: RSubConnectionStatus) {
        object Connecting : ConnectionState(RSubConnectionStatus.CONNECTING)
        class Connected(
            val send: suspend (message: RSubMessage) -> Unit,
            val incoming: Flow<RSubMessage>
        ) : ConnectionState(RSubConnectionStatus.CONNECTED)

        object Disconnected : ConnectionState(RSubConnectionStatus.DISCONNECTED)
    }
}
