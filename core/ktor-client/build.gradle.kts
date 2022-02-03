plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.android-library")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:di"))

                api(libs.ktor.client.core)
                api(libs.ktor.client.serialization)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(libs.ktor.client.android)
            }
        }

        named("jvmMain") {
            dependencies {
                implementation(libs.ktor.client.java)
            }
        }
    }
}
