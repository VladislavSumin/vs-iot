plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":core:id"))
            }
        }
    }
}
