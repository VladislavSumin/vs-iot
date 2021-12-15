plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                api(libs.logging.kermit)
                implementation(libs.logging.log4j.api)
                implementation(libs.logging.log4j.core)
                implementation(libs.logging.log4j.slf4j)
                implementation(libs.logging.slf4j)
            }
        }
    }
}
