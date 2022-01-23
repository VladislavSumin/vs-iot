package ru.vs.iot.decompose.view_model

import com.arkivanov.essenty.instancekeeper.InstanceKeeper

abstract class ViewModel : InstanceKeeper.Instance {
    override fun onDestroy() {
        println("BBBBBBBB")
    }
}
