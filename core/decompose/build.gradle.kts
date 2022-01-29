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

                api(libs.kodein.compose)

                implementation(project(":core:compose"))
                implementation(project(":core:logging"))
            }
        }
        named("androidMain") {
            dependencies {
                api(libs.decompose.android)
            }
        }
    }
}
