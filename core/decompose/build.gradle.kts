plugins {
    id("ru.vs.convention.multiplatform.android-library")
    id("ru.vs.convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.decompose.core)
                api(libs.decompose.jetbrains)

                api(libs.kodein.compose)

                implementation(libs.vs.compose)
                implementation(libs.vs.logging.core)
            }
        }
        named("androidMain") {
            dependencies {
                api(libs.decompose.android)
            }
        }
    }
}
