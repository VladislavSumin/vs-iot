package ru.vs.rsub

@RSubInterface
interface TestInterface {
    suspend fun unitSuspend(): Unit
    suspend fun stringSuspend(): String
    suspend fun intSuspend(): Int
    suspend fun longSuspend(): Long

    suspend fun listStringSuspend(): List<String>
    suspend fun setStringSuspend(): Set<String>
    suspend fun mapStringStringSuspend(): Map<String, String>
}
