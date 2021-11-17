package ru.vs.iot.build_script.default_servers

import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.NamedDomainObjectList
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
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
        val task = project.tasks.register<DefaultServersTask>(getDefaultServersTaskName(variant))
        variant.javaCompileProvider.dependsOn(task)
    }

    private fun attachDefaultServersContainer(obj: Any) {
        val defaultServers = project.objects.namedDomainObjectList(DefaultServer::class)
        (obj as ExtensionAware).extensions.add(DEFAULT_SERVERS_EXTENSION_NAME, defaultServers)
    }

    private fun getDefaultServersTaskName(variant: BaseVariant) = DEFAULT_SERVERS_TASK_NAME + variant.name.capitalize()
}