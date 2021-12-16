plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                api(project(":rsub:connector:ktor-websocket:core"))
                api(project(":rsub:server"))

                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.websockets)
            }
        }
    }
}
