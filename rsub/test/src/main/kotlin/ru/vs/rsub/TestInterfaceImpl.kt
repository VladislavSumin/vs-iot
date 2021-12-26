package ru.vs.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestInterfaceImpl : TestInterface {
    override suspend fun unitSuspend() {
        // no action
    }

    override suspend fun stringSuspend(): String {
        return "string"
    }

    override suspend fun intSuspend(): Int {
        return 1245678
    }

    override suspend fun longSuspend(): Long {
        return 1234567890L
    }

    override suspend fun listStringSuspend(): List<String> {
        return listOf("string1", "string2", "string3")
    }

    override suspend fun setStringSuspend(): Set<String> {
        return setOf("string1", "string2", "string3")
    }

    override suspend fun mapStringStringSuspend(): Map<String, String> {
        return mapOf("key1" to "string1", "key2" to "string2", "key3" to "string3")
    }

    override fun stringFlow(): Flow<String> {
        return flowOf("string1", "string2", "string3")
    }
}
