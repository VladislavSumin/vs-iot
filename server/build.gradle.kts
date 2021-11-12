plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.ktor.server.netty)

    implementation(libs.logging.kermit)
    implementation(libs.logging.log4j.api)
    implementation(libs.logging.log4j.core)
    implementation(libs.logging.log4j.slf4j)
    implementation(libs.logging.slf4j)
}