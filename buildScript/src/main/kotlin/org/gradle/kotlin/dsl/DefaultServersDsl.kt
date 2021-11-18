package org.gradle.kotlin.dsl

import com.android.build.api.dsl.BaseFlavor
import com.android.build.api.dsl.BuildType
import org.gradle.api.NamedDomainObjectContainer
import ru.vs.iot.build_script.Network
import ru.vs.iot.build_script.default_servers.DefaultServer
import ru.vs.iot.build_script.default_servers.DefaultServersPlugin

fun BaseFlavor.defaultServers(block: NamedDomainObjectContainer<DefaultServer>.() -> Unit) {
    DefaultServersPlugin.getDefaultServers(this).block()
}

fun BuildType.defaultServers(block: NamedDomainObjectContainer<DefaultServer>.() -> Unit) {
    DefaultServersPlugin.getDefaultServers(this).block()
}

fun NamedDomainObjectContainer<DefaultServer>.createLocalMachineDevServer() {
    this.create("Local dev server") {
        val ip = Network.getLocalIpV4Addresses().first()
        url = "http://${ip.hostName}:8080"
    }
}
