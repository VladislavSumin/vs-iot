package ru.vs.rsub

class RSubServer {

    suspend fun handleConnection(connection: RSubConnection) {
        ConnectionHandler(connection).handle()
    }

    private inner class ConnectionHandler(
        private val connection: RSubConnection
    ) {
        suspend fun handle() {
            // no action now
        }
    }
}
