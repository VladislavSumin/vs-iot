plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("convention.android.library-multiplatform")
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
