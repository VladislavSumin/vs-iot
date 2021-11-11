package ru.vs.iot.server

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter


fun main() {
    Logger.setLogWriters(platformLogWriter())
    Logger.setTag("server")
    Logger.i { "ytetsasf" }
}