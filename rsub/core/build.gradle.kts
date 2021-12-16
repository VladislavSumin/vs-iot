plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:coroutines"))
            }
        }
    }
}
