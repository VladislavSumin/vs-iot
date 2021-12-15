plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                api(libs.logging.kermit)
                api(libs.logging.log4j.api)
                api(libs.logging.log4j.core)
                api(libs.logging.log4j.slf4j)
                api(libs.logging.slf4j)
            }
        }
    }
}
