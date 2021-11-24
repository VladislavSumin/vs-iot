plugins {
    kotlin("multiplatform")
    id("convention.android.library-multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    jvm()

    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.kodein.core)
            }
        }
        named("androidMain") {
            dependencies {
                api(libs.kodein.compose)
            }
        }
    }
}
