package ru.vs.rsub.server

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.vs.rsub.RSubMessage

class ServerTest : BaseServerTest() {
    @Test
    fun test(): Unit = runBlocking {
        initConnection()
        sendChannel.send(getSubscribeMessage("stringSuspend"))
        val rawResponse = receiveChannel.receive()
        val responseMsg = Json.decodeFromString<RSubMessage>(rawResponse) as RSubMessage.Data
        assertEquals(testInterface.stringSuspend(), Json.decodeFromJsonElement<String>(responseMsg.data!!))
    }

    private fun getSubscribeMessage(functionName: String): String {
        val msg = RSubMessage.Subscribe(0, "TestInterface", functionName)
        return Json.encodeToString<RSubMessage>(msg)
    }
}
