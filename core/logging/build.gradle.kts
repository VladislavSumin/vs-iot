plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.logging.kermit)
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(libs.logging.log4j.api)
                implementation(libs.logging.log4j.core)
                implementation(libs.logging.log4j.slf4j)
                implementation(libs.logging.slf4j)
            }
        }
    }
}
