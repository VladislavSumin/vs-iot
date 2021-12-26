package ru.vs.rsub

import kotlin.reflect.KType
import kotlin.reflect.typeOf

sealed interface RSubServerSubscription {
    val type: KType

    interface SuspendSub<T> : RSubServerSubscription {
        suspend fun get(): T
    }

//    fun interface FlowSub<T> {
//        fun get(): Flow<T>
//    }

    companion object {
        inline fun <reified T> createSuspend(crossinline method: suspend () -> T): SuspendSub<T> {
            return object : SuspendSub<T> {
                override val type: KType = typeOf<T>()

                override suspend fun get(): T {
                    return method()
                }
            }
        }
    }
}
