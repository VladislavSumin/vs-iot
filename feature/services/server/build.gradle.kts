plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(libs.vs.coroutines)
                implementation(libs.vs.di)
                implementation(project(":core:serialization"))
            }
        }
    }
}
