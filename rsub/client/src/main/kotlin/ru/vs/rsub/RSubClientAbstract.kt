package ru.vs.rsub

open class RSubClientAbstract(protected val connector: RSubConnector) {
    protected suspend fun <T> processSuspend(
        interfaceName: String,
        methodName: String,
    ): T {
        return TODO()
    }
}
