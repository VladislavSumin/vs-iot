plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(libs.vs.coroutines)
    api(libs.vs.logging.core)
    api(libs.kotlin.serialization.core)
    api(libs.kotlin.serialization.json)
}
