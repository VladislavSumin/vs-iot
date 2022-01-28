plugins {
    id("convention.multiplatform.jvm")
    id("convention.android.library-multiplatform")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.serialization)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(libs.ktor.client.android)
            }
        }

        named("jvmMain") {
            dependencies {
            }
        }
    }
}