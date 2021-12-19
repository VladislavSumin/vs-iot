package ru.vs.rsub

open class RSubServerSubscriptionsAbstract {
    protected val impls: MutableMap<String, InterfaceWrapperAbstract> = mutableMapOf()

    abstract class InterfaceWrapperAbstract {
        val methodImpls: MutableMap<String, RSubServerSubscription> = mutableMapOf()
        init {
            methodImpls[""] = RSubServerSubscription.SuspendSub<Unit> { Unit }
        }
    }
}
