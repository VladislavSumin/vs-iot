plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":core:coroutines"))
    implementation(libs.kotlin.serialization.core)
    implementation(libs.kotlin.serialization.json)
}