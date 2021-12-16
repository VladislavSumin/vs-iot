plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":rsub:connector:ktor-websocket:core"))
                api(project(":rsub:client"))

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.websockets)
            }
        }
    }
}
