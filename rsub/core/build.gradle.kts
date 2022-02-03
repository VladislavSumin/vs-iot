plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(libs.vs.coroutines)
    api(project(":core:logging"))
    api(libs.kotlin.serialization.core)
    api(libs.kotlin.serialization.json)
}
