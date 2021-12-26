package ru.vs.rsub.client_server

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ClientServerTest : ClientServerBaseTest() {
    @Test
    fun `success call suspend function with unit return type`(): Unit = runBlocking {
        assertEquals(testInterface.unitSuspend(), client.testInterface.unitSuspend())
    }

    @Test
    fun `success call suspend function with int return type`(): Unit = runBlocking {
        assertEquals(testInterface.intSuspend(), client.testInterface.intSuspend())
    }

    @Test
    fun `success call suspend function with long return type`(): Unit = runBlocking {
        assertEquals(testInterface.longSuspend(), client.testInterface.longSuspend())
    }

    @Test
    fun `success call suspend function with string return type`(): Unit = runBlocking {
        assertEquals(testInterface.stringSuspend(), client.testInterface.stringSuspend())
    }

    @Test
    fun `success call suspend function with list string return type`(): Unit = runBlocking {
        assertEquals(testInterface.listStringSuspend(), client.testInterface.listStringSuspend())
    }

    @Test
    fun `success call suspend function with set string return type`(): Unit = runBlocking {
        assertEquals(testInterface.setStringSuspend(), client.testInterface.setStringSuspend())
    }

    @Test
    fun `success call suspend function with map string string return type`(): Unit = runBlocking {
        assertEquals(testInterface.mapStringStringSuspend(), client.testInterface.mapStringStringSuspend())
    }
}
