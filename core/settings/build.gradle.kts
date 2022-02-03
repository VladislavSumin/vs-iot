plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.android-library")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":core:di"))
                api(libs.multiplatformSettings.core)
            }
        }
    }
}
