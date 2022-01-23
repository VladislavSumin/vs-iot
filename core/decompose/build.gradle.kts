plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.decompose.core)
                api(libs.decompose.jetbrains)
                implementation(project(":core:compose2"))
            }
        }
        named("androidMain") {
            dependencies {
                api(libs.decompose.android)
            }
        }
    }
}
