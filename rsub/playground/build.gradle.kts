plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":core:ktor-server"))
            }
        }
    }
}
