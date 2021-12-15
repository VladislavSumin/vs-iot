plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.kotlin.coroutines.core)
            }
        }
    }
}
