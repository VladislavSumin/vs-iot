plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":rsub:client"))
    implementation(libs.ksp)
    implementation(libs.kotlinpoet.core)
    implementation(libs.kotlinpoet.ksp)
}