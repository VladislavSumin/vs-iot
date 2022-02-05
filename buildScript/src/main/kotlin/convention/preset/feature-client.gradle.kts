package convention.preset

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("ru.vs.convention.multiplatform.android-library")
    id("ru.vs.convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("convention.multiplatform.resources")
}

val libs = the<LibrariesForLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.vs.coroutines)
                implementation(libs.vs.di)
                implementation(libs.vs.logging.core)
                implementation(project(":core:compose"))
                implementation(project(":core:decompose"))
                implementation(project(":core:ktor-client"))
                implementation(project(":core:navigation"))
                implementation(project(":core:serialization"))
                implementation(project(":core:settings"))
                implementation(project(":core:uikit"))
                implementation(project(":core:utils"))
            }
        }
    }
}
