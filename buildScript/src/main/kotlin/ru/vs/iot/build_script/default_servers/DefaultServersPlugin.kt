package ru.vs.iot.build_script.default_servers

import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectList
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.container
import org.gradle.kotlin.dsl.defaultServers
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.namedDomainObjectList
import org.gradle.kotlin.dsl.register
import ru.vs.iot.build_script.utils.android
import ru.vs.iot.build_script.utils.variants

private const val DEFAULT_SERVERS_TASK_NAME = "generateDefaultServers"
private const val DEFAULT_SERVERS_EXTENSION_NAME = "defaultServers"

class DefaultServersPlugin : Plugin<Project> {
    private lateinit var project: Project
    override fun apply(target: Project) {
        project = target

        project.android {
            defaultConfig(::attachDefaultServersContainer)
            buildTypes.configureEach(::attachDefaultServersContainer)
            productFlavors.configureEach(::attachDefaultServersContainer)

            variants.configureEach(::setupDefaultServersTask)
        }
    }

    private fun setupDefaultServersTask(variant: BaseVariant) {
        val task = project.tasks.register<DefaultServersTask>(getDefaultServersTaskName(variant)) {
            val defaultServersProvider = project.provider {
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
            defaultServers.set(defaultServersProvider)
        }
        variant.javaCompileProvider.dependsOn(task)
    }

    private fun attachDefaultServersContainer(obj: Any) {
        val defaultServers = project.container(DefaultServer::class)
        (obj as ExtensionAware).extensions.add(DEFAULT_SERVERS_EXTENSION_NAME, defaultServers)
    }

    private fun getDefaultServersTaskName(variant: BaseVariant) = DEFAULT_SERVERS_TASK_NAME + variant.name.capitalize()

    companion object {
        private fun Any.getDefaultServers() = getDefaultServers(this)

        internal fun getDefaultServers(any: Any) =
            (any as ExtensionAware).extensions[DEFAULT_SERVERS_EXTENSION_NAME] as NamedDomainObjectContainer<DefaultServer>
    }
}