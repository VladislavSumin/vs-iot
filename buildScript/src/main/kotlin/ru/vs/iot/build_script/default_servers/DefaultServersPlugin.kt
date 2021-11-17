package ru.vs.iot.build_script.default_servers

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.vs.iot.build_script.utils.android

class DefaultServersPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.android {
//            sourceSets {
//                named("main") {
//                    java.srcDir("build/generated/source/lol/main")
//                }
//            }
//            variants {
//                configureEach {
//                    this.javaCompileProvider
//                }
//            }
        }
    }
}