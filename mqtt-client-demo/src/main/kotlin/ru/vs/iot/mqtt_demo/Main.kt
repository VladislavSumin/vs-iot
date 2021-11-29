package ru.vs.iot.mqtt_demo

import org.eclipse.paho.mqttv5.client.MqttAsyncClient
import org.eclipse.paho.mqttv5.common.MqttMessage

fun main() {
    val client = MqttAsyncClient("tcp://localhost:1883", "vs-iot-server")
    client.connect().waitForCompletion()
    println("a")
    client.publish("/a/a/a", MqttMessage("Hello".toByteArray())).waitForCompletion()
    println("b")
    client.disconnect().waitForCompletion()
    println("c")
    client.close()
}
