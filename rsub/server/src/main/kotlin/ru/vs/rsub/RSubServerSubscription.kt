package ru.vs.rsub

import kotlinx.coroutines.flow.Flow

sealed interface RSubServerSubscription {
    fun interface SuspendSub<T> : RSubServerSubscription {
        suspend fun get(): T
    }

    fun interface FlowSub<T> {
        fun get(): Flow<T>
    }
}
