package ru.vs.iot.build_script.default_servers

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.container
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import ru.vs.iot.build_script.utils.android
import ru.vs.iot.build_script.utils.namedOrNull
import ru.vs.iot.build_script.utils.variants

private const val DEFAULT_SERVERS_TASK_NAME = "generateDefaultServers"
private const val DEFAULT_SERVERS_EXTENSION_NAME = "defaultServers"

class DefaultServersPlugin : Plugin<Project> {
    private lateinit var project: Project

    // Обраться по правильному из android.sourceSets нельзя так как конфигурация блокируется
    // на этапе создания вариантов.
    // Похожий способ использует плагин sqldelight
    // https://github.com/cashapp/sqldelight/blob/master/sqldelight-gradle-plugin/src/main/kotlin/com/squareup/sqldelight/gradle/kotlin/SourceRoots.kt
    private lateinit var androidSourceSets: NamedDomainObjectContainer<out AndroidSourceSet>

    override fun apply(target: Project) {
        project = target

        project.android {
            defaultConfig(::attachDefaultServersContainer)
            buildTypes.configureEach(::attachDefaultServersContainer)
            productFlavors.configureEach(::attachDefaultServersContainer)
            androidSourceSets = sourceSets
            variants.configureEach(::setupDefaultServersTask)
        }
    }

    private fun setupDefaultServersTask(variant: BaseVariant) {
        val outDir = project.layout.buildDirectory
            .dir("generated/source/default_servers/${variant.name}")

        androidSourceSets.named(variant.name) {
            kotlin.srcDir(outDir)
        }

        val task = project.tasks.register<DefaultServersTask>(getDefaultServersTaskName(variant)) {
            defaultServers.set(getDefaultServersProvider(variant))
            outputDirectory.set(outDir)
        }

        project.tasks.named("compile${variant.name.capitalize()}Kotlin").dependsOn(task)
        // TODO почитать подробнее про стабы и вызывает ли студия эту таску
        project.tasks.namedOrNull("kaptGenerateStubs${variant.name.capitalize()}Kotlin")?.dependsOn(task)
    }

    private fun getDefaultServersProvider(variant: BaseVariant) = project.provider {
        // Собираем все DefaultServer для конкретного варианта
        sequence {
            yieldAll(project.android.defaultConfig.getDefaultServers())
            yieldAll(
                variant.productFlavors.flatMap {
                    project.android.productFlavors.named(it.name).get().getDefaultServers()
                }
            )
            yieldAll(project.android.buildTypes.named(variant.buildType.name).get().getDefaultServers())
        }.toList()
    }

    private fun attachDefaultServersContainer(obj: Any) {
        val defaultServers = project.container(DefaultServer::class)
        (obj as ExtensionAware).extensions.add(DEFAULT_SERVERS_EXTENSION_NAME, defaultServers)
    }

    private fun getDefaultServersTaskName(variant: BaseVariant) = DEFAULT_SERVERS_TASK_NAME + variant.name.capitalize()

    companion object {
        private fun Any.getDefaultServers() = getDefaultServers(this)

        internal fun getDefaultServers(any: Any) =
            (any as ExtensionAware).extensions[DEFAULT_SERVERS_EXTENSION_NAME]
                as NamedDomainObjectContainer<DefaultServer>
    }
}
