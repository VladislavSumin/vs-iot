plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:compose2"))
                implementation(project(":core:decompose"))
            }
        }
    }
}
