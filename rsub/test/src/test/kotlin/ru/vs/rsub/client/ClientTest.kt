package ru.vs.rsub.client

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import ru.vs.rsub.RSubMessage

class ClientTest : BaseClientTest() {
    @Test
    fun `success call suspend function with string return type`(): Unit = runBlocking {
        testSuspend("test", client.testInterface::stringSuspend)
    }

    @Test
    fun `success call suspend function with int return type`(): Unit = runBlocking {
        testSuspend(123456, client.testInterface::intSuspend)
    }

    @Test
    fun `success call suspend function with long return type`(): Unit = runBlocking {
        testSuspend(123456789L, client.testInterface::longSuspend)
    }

    private suspend inline fun <reified T : Any> testSuspend(
        testData: T,
        noinline method: suspend () -> T
    ) = coroutineScope {
        val resultDeferred = async { method() }

        val rawSubscribeMessage = receiveFromClientChannel.receive()
        val subscribeMessage = Json.decodeFromString<RSubMessage>(rawSubscribeMessage)
        assertInstanceOf(RSubMessage.Subscribe::class.java, subscribeMessage)

        val message = RSubMessage.Data(0, Json.encodeToJsonElement(testData))
        sendToClientChannel.send(Json.encodeToString<RSubMessage>(message))

        val result = resultDeferred.await()
        assertEquals(testData, result)
    }
}
