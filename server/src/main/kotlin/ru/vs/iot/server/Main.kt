package ru.vs.iot.server

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.*
import org.apache.logging.log4j.LogManager
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.iot.server.web.WebServer

val serverScope by lazy { ServerScope(doOnCancellation = ::closeLogger) }

fun main() {
    // Setup logger
    Logger.setLogWriters(platformLogWriter())
    Logger.setTag("server")
    Logger.i("Server starting")

    serverScope.launch { runServer() }

    // Мы не должны завершать main thread так как все шедулеры корутин используют демонические потоки
    serverScope.blockingAwait()
}

private suspend fun runServer() {
    Di.direct.instance<WebServer>().run()
}

private suspend fun closeLogger() {
    Logger.i("Shooting down logger")
    // Log4j2 logger doesn't close automatically. We must close them manually.
    LogManager.shutdown()
}

