package ru.vs.rsub

@RSubInterface
interface TestInterface {
    suspend fun unitSuspend(): Unit
    suspend fun stringSuspend(): String
    suspend fun intSuspend(): Int
    suspend fun longSuspend(): Long
}
