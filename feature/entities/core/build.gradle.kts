plugins {
    id("convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:di"))
                implementation(project(":core:serialization"))

                api(project(":feature:entities:dto"))
            }
        }
    }
}