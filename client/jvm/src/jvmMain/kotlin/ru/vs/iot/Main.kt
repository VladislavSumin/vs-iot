package ru.vs.iot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.vs.iot.uikit.theme.MainTheme

fun main() {
    println("JVM client")
    application {
        Window(
            onCloseRequest = this::exitApplication,
            state = rememberWindowState(),
            title = "vs-iot"
        ) {
            Surface(Modifier.fillMaxSize()) {
                MainTheme {
                    Button(onClick = {}) {
                        Text("Hello button")
                    }
                }
            }
        }
    }
}
