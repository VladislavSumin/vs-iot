package ru.vs.rsub

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

abstract class RSubServerAbstract(
    private val json: Json = Json,
    private val logger: Logger = Logger.withTag("RSubClient"),
) {
    suspend fun handleNewConnection(connection: RSubConnection): Unit = coroutineScope {
        ConnectionHandler(connection).handle()
    }

    protected abstract fun findSubscription(interfaceName: String, methodName: String): Subscription

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
                        else -> {
                            // TODO!!
                        }
                    }
                }
                activeSubscriptions.forEach { (_, v) -> v.cancel() }
            }
            logger.d("Connection closed")
        }

        private suspend fun send(message: RSubMessage) {
            connection.send(json.encodeToString(message))
        }

        // TODO check possible data corrupt on id error from client (add sync?)
        // TODO add error handling
        // TODO make cancelable
        private suspend fun processSubscribe(request: RSubMessage.Subscribe, scope: CoroutineScope) {
            val job = scope.launch(start = CoroutineStart.LAZY) {
//                logger.v("Subscribe id=${request.id} to ${request.interfaceName}::${request.functionName}")
//                val rSubInterface = impls[request.interfaceName]!!.first
//                val instance = impls[request.interfaceName]!!.second
//                val kFunction = rSubInterface.functions.find { it.name == request.functionName }!!
//
//                try {
//                    if (kFunction.isSuspend) {
//                        val response = suspendCoroutine<Any?> {
//                            it.resume(kFunction.call(instance, it))
//                        }
//                        sendData(request.id, kFunction.returnType, response)
//                    } else {
//                        val flow = kFunction.call(instance) as Flow<*>
//                        flow.collect {
//                            sendData(request.id, kFunction.returnType.arguments[0].type!!, it)
//                        }
//                        send(RSubMessage.FlowComplete(request.id))
//                    }
//                } catch (e: Exception) {
//                    send(RSubMessage.Error(request.id))
//                    activeSubscriptions.remove(request.id)
//
//                    if (e is CancellationException) throw e
//                    log.trace(
//                        "Error on subscription id=${request.id} to ${request.interfaceName}::${request.functionName}",
//                        e
//                    )
//                    return@launch
//                }
//
//
//                log.trace("Complete subscription id=${request.id} to ${request.interfaceName}::${request.functionName}")
//                activeSubscriptions.remove(request.id)
            }
            activeSubscriptions[request.id] = job
            job.start()
        }

        private fun processUnsubscribe(request: RSubMessage) {
            logger.v("Cancel subscription id=${request.id}")
            activeSubscriptions.remove(request.id)?.cancel()
        }

        private suspend fun sendData(id: Int, type: KType, data: Any?) {
            val responsePayload =
                json.encodeToJsonElement(
                    json.serializersModule.serializer(type),
                    data
                )
            val message = RSubMessage.Data(
                id,
                responsePayload
            )
            send(message)
        }
    }

    protected interface Subscription

    protected abstract class SuspendSubscription<T : Any> : Subscription {
        abstract suspend fun get(): T
    }

    protected abstract class FlowSubscription<T : Any> : Subscription {
        abstract fun get(): Flow<T>
    }
}
