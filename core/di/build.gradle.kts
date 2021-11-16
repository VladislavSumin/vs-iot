plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.kodein.core)
            }
        }
    }
}

group = "ololo"