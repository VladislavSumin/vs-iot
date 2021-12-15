plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                api(libs.ktor.server.netty)
                api(libs.ktor.server.serialization)
            }
        }
    }
}
