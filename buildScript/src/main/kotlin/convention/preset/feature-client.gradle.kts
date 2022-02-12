package convention.preset

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.accessors.dm.LibrariesForVsLibs

plugins {
    id("ru.vs.convention.multiplatform.android-library")
    id("ru.vs.convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("ru.vs.convention.multiplatform.resources")
}

val libs = the<LibrariesForLibs>()
val vsLibs = the<LibrariesForVsLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(vsLibs.vs.core.coroutines)
                implementation(vsLibs.vs.core.di)
                implementation(vsLibs.vs.core.logging.core)
                implementation(vsLibs.vs.core.compose)
                implementation(vsLibs.vs.core.decompose)
                implementation(vsLibs.vs.core.navigation)
                implementation(vsLibs.vs.core.uikit)
                implementation(vsLibs.vs.core.ktor.client)
                implementation(vsLibs.vs.core.serialization)
                implementation(project(":core:settings"))
                implementation(vsLibs.vs.core.utils)
            }
        }
    }
}

// TODO очень плохая идя вычислять пакет таким образом
fun getPackageName(): String {
    val projectName = project.name
    return if (projectName == "client") {
        project.parent!!.name
    } else {
        projectName
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.vs.iot.${getPackageName()}"
}
