package ru.vs.iot.build_script.default_servers

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

private const val BASE_PACKAGE = "ru.vs.iot.default_servers.repository"
private const val CLASS_NAME = "DefaultServersRepositoryImpl"

@CacheableTask
abstract class DefaultServersTask : DefaultTask() {
    @get:Nested
    abstract val defaultServers: ListProperty<DefaultServer>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun action() {
        val outputDir = outputDirectory.get().asFile
        generateClass(outputDir)
    }

    private fun generateClass(outputDir: File) {
        val servers = defaultServers.get().joinToString(separator = ",") {
            "DefaultServer(\"${it.name}\", \"${it.url}\")"
        }

        val getDefaultServersFunction = FunSpec.builder("getDefaultServers")
            .addModifiers(KModifier.SUSPEND, KModifier.OVERRIDE)
            .returns(
                ClassName("kotlin.collections", "List")
                    .parameterizedBy(ClassName(BASE_PACKAGE, "DefaultServer"))
            )
            .addCode(
                """
                |return listOf(
                |   $servers
                |)
                """.trimMargin()
            )
            .build()

        val clazz = TypeSpec.classBuilder(ClassName(BASE_PACKAGE, CLASS_NAME))
            .addSuperinterface(ClassName(BASE_PACKAGE, "DefaultServersRepository"))
            .addModifiers(KModifier.INTERNAL)
            .addFunction(getDefaultServersFunction)
            .build()

        val file = FileSpec.builder(BASE_PACKAGE, CLASS_NAME)
            .addType(clazz)
            .build()

        file.writeTo(outputDir)
    }
}
