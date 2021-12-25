package ru.vs.rsub

import kotlin.reflect.KType

sealed interface RSubServerSubscription {
    val type: KType

    interface SuspendSub<T> : RSubServerSubscription {
        suspend fun get(): T
    }

//    fun interface FlowSub<T> {
//        fun get(): Flow<T>
//    }

    companion object {
        fun <T> createSuspend(method: suspend () -> T, type: KType): SuspendSub<T> {
            return object : SuspendSub<T> {
                override val type: KType = type

                override suspend fun get(): T {
                    return method()
                }
            }
        }
    }
}
