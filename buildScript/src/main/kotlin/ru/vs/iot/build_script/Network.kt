package ru.vs.iot.build_script

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface

object Network {
    private fun getLocalIpAddresses(): Sequence<InetAddress> {
        return NetworkInterface.getNetworkInterfaces().asSequence()
            .filter { it.isUp && !it.isLoopback && !it.isVirtual }
            .flatMap { it.interfaceAddresses }
            .map { it.address }
            .filter { !it.isLoopbackAddress }
    }

    fun getLocalIpV4Addresses(): Sequence<Inet4Address> {
        return getLocalIpAddresses().filterIsInstance<Inet4Address>()
    }
}
