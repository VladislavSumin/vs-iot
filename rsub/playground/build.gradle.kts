plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":core:ktor-server"))
                implementation(project(":rsub:server"))
                implementation(project(":rsub:connector:ktor-websocket:server"))
            }
        }
    }
}
