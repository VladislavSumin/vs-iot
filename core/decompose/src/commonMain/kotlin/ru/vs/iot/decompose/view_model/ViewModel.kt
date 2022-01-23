package ru.vs.iot.decompose.view_model

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class ViewModel : InstanceKeeper.Instance {
    protected val viewModelScope = CoroutineScope(Dispatchers.Main)
    override fun onDestroy() {
        viewModelScope.cancel()
        println("BBBBBBBB")
    }
}
