plugins {
    // TODO с jvm модулями все плохо
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    id("com.github.gmazzo.buildconfig")
}

val version: String by project
buildConfig {
    className("BuildConfig")
    packageName("ru.vs.iot.server")
    buildConfigField("String", "version", "\"$version\"")
}

kotlin {
    jvm()

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":core:di"))

                implementation(libs.kotlin.coroutines.core)

                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.serialization)

                implementation(libs.logging.kermit)
                implementation(libs.logging.log4j.api)
                implementation(libs.logging.log4j.core)
                implementation(libs.logging.log4j.slf4j)
                implementation(libs.logging.slf4j)
            }
        }
    }
}
