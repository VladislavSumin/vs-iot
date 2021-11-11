plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.ktor.server.netty)
}