plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(vsLibs.vs.core.coroutines)
    api(vsLibs.vs.core.logging.core)
    api(libs.kotlin.serialization.core)
    api(libs.kotlin.serialization.json)
}
