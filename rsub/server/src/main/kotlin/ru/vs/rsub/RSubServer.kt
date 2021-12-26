package ru.vs.rsub

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

class RSubServer(
    private val rSubServerSubscriptions: RSubServerSubscriptionsAbstract,
    private val json: Json = Json,
    private val logger: Logger = Logger.withTag("RSubServer"),
) {
    suspend fun handleNewConnection(connection: RSubConnection): Unit = coroutineScope {
        ConnectionHandler(connection).handle()
    }

    private inner class ConnectionHandler(
        private val connection: RSubConnection
    ) {
        private val activeSubscriptions = mutableMapOf<Int, Job>()

        suspend fun handle() {
            coroutineScope {
                logger.d("Handle new connection")
                connection.receive.collect {
                    when (val request = Json.decodeFromString<RSubMessage>(it)) {
                        is RSubMessage.Subscribe -> processSubscribe(request, this)
                        is RSubMessage.Unsubscribe -> processUnsubscribe(request)
                        else -> throw RSubException("Unexpected message type $request")
                    }
                }
            }
            activeSubscriptions.forEach { (_, v) -> v.cancel() }
            connection.close()
            logger.d("Connection closed")
        }

        private suspend fun send(message: RSubMessage) {
            connection.send(json.encodeToString(message))
        }

        // TODO check possible data corrupt on id error from client (add sync?)
        // TODO add error handling
        // TODO make cancelable
        @Suppress("TooGenericExceptionCaught")
        private suspend fun processSubscribe(request: RSubMessage.Subscribe, scope: CoroutineScope) {
            val job = scope.launch(start = CoroutineStart.LAZY) {
                logger.v("Subscribe id=${request.id} to ${request.interfaceName}::${request.functionName}")

                val impl = rSubServerSubscriptions.getImpl(request.interfaceName, request.functionName)

                try {
                    when (impl) {
                        is RSubServerSubscription.SuspendSub<*> -> {
                            val response = impl.get()
                            sendData(request.id, response, impl.type)
                        }
                        is RSubServerSubscription.FlowSub<*> -> {
                            val flow = impl.get()
                            flow.collect { sendData(request.id, it, impl.type) }
                            send(RSubMessage.FlowComplete(request.id))
                        }
                    }
                } catch (e: Exception) {
                    send(RSubMessage.Error(request.id))
                    activeSubscriptions.remove(request.id)

                    if (e is CancellationException) throw e
                    logger.v(
                        "Error on subscription id=${request.id} to ${request.interfaceName}::${request.functionName}",
                        e
                    )
                    return@launch
                }

                logger.v("Complete subscription id=${request.id} to ${request.interfaceName}::${request.functionName}")
                activeSubscriptions.remove(request.id)
            }
            activeSubscriptions[request.id] = job
            job.start()
        }

        private fun processUnsubscribe(request: RSubMessage) {
            logger.v("Cancel subscription id=${request.id}")
            activeSubscriptions.remove(request.id)?.cancel()
        }

        private suspend fun sendData(id: Int, data: Any?, type: KType) {
            val responsePayload = json.encodeToJsonElement(json.serializersModule.serializer(type), data)
            val message = RSubMessage.Data(id, responsePayload)
            send(message)
        }
    }
}
