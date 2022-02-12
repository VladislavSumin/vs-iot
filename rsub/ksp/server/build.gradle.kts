plugins {
    kotlin("jvm")
}

dependencies {
    implementation(vsLibs.vs.core.utils)
    implementation(project(":rsub:server"))
    implementation(libs.ksp)
    implementation(libs.kotlinpoet.core)
    implementation(libs.kotlinpoet.ksp)
}
