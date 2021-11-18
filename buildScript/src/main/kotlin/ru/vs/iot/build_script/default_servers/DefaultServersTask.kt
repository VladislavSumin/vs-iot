package ru.vs.iot.build_script.default_servers

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction

private const val BASE_PACKAGE = "ru.vs.iot.repository"
private const val CLASS_NAME = "DefaultServersRepositoryImpl"

abstract class DefaultServersTask : DefaultTask() {
    @get:Nested
    abstract val defaultServers: ListProperty<DefaultServer>

    @TaskAction
    fun action() {
        generateClass()
    }

    private fun generateClass() {
        val clazz = TypeSpec.classBuilder(ClassName(BASE_PACKAGE, CLASS_NAME))
            .addSuperinterface(ClassName(BASE_PACKAGE, "ServersRepository"))
            .build()

        val file = FileSpec.builder(BASE_PACKAGE, CLASS_NAME)
            .addType(clazz)
            .build()

        file.writeTo(System.out)
    }
}