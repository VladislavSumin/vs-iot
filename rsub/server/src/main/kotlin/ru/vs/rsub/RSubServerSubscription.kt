package ru.vs.rsub

import kotlinx.coroutines.flow.Flow

interface RSubServerSubscription {
    interface SuspendSub<T> : RSubServerSubscription {
        suspend fun get(): T
    }

    interface FlowSub<T> {
        fun get(): Flow<T>
    }
}
