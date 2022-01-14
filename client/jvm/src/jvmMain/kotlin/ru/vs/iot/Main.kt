package ru.vs.iot

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() {
    println("JVM client")
    application {
        Window(
            onCloseRequest = this::exitApplication,
            state = rememberWindowState(),
            title = "vs-iot"
        ) {
        }
    }
}
