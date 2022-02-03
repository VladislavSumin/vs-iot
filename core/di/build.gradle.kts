plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.android-library")
}

kotlin {
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
