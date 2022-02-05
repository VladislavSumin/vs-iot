plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.vs.di)

                api(libs.kotlin.serialization.json)
            }
        }
    }
}
