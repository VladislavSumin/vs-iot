plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":core:uikit"))
                api(libs.decompose.core)
                api(libs.decompose.jetbrains)
            }
        }
        named("androidMain") {
            dependencies {
                api(libs.decompose.android)
            }
        }
    }
}
