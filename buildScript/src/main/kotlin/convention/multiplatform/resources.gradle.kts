package convention.multiplatform

import dev.icerock.gradle.MRVisibility
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("convention.multiplatform.jvm")
    id("dev.icerock.mobile.multiplatform-resources")
}

val libs = the<LibrariesForLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.moko.resources.core)
                implementation(libs.moko.resources.compose)
            }
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.vs.iot.${project.name}"
    multiplatformResourcesVisibility = MRVisibility.Internal
}
