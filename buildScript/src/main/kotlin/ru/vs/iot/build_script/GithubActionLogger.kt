package ru.vs.iot.build_script

object GithubActionLogger {
    fun w(message: String) {
        println("::warning::$message")
    }
}
