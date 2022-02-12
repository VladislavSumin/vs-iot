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
                implementation(libs.vs.coroutines)
                implementation(libs.vs.di)
                implementation(libs.vs.logging.core)
                implementation(libs.vs.compose)
                implementation(libs.vs.decompose)
                implementation(libs.vs.navigation)
                implementation(libs.vs.uikit)
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
