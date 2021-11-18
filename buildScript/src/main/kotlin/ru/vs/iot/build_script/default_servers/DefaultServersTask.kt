package ru.vs.iot.build_script.default_servers

import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction

abstract class DefaultServersTask : DefaultTask() {
    @get:Nested
    abstract val defaultServers: ListProperty<DefaultServer>

    @TaskAction
    fun action() {
    }
}