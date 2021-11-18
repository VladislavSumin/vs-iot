package ru.vs.iot.build_script.default_servers

import org.gradle.api.tasks.Input

class DefaultServer(
    @get:Input
    val name: String,
) {
    @get:Input
    var url: String = ""
}