package ru.vs.rsub

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

open class RSubClientAbstract(
    protected val connector: RSubConnector,
    protected val scope: CoroutineScope = GlobalScope
) {
    protected suspend fun <T> processSuspend(
        interfaceName: String,
        methodName: String,
    ): T {
        return TODO()
    }
}
