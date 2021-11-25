plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    jvm()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.kotlin.serialization.core)
            }
        }
    }
}
