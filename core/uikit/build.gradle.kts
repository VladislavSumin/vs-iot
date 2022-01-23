plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":core:compose2"))
            }
        }
    }
}
