plugins {
    id("convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:di"))

                api(libs.kotlin.serialization.json)
            }
        }
    }
}
