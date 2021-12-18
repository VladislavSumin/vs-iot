plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":rsub:server"))
    implementation(libs.ksp)
    implementation(libs.kotlinpoet.core)
    implementation(libs.kotlinpoet.ksp)
}