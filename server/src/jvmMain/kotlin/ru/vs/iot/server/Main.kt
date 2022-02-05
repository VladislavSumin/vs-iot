package ru.vs.iot.server

import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.core.logging.setupDefault
import ru.vs.core.logging.shutdown
import ru.vs.iot.server.domain.about.AboutServerInteractor
import ru.vs.iot.server.web.WebServer

val serverScope by lazy { ServerScope(doOnCancellation = ::closeLogger) }

fun main() {
    // Setup logger
    Logger.setupDefault()
    Logger.setTag("server")

    val aboutServerInteractor: AboutServerInteractor by Di.instance()

    serverScope.launch {
        Logger.i("Starting server. Version: ${aboutServerInteractor.getVersion()}")
        runServer()
    }

    // Мы не должны завершать main thread так как все шедулеры корутин используют демонические потоки
    serverScope.blockingAwait()
}

private suspend fun runServer() {
    Di.direct.instance<WebServer>().run()
}

private suspend fun closeLogger() {
    Logger.i("Shooting down logger")
    // Log4j2 logger doesn't close automatically. We must close them manually.
    Logger.shutdown()
}
