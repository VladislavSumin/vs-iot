plugins {
    id("convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":core:coroutines"))
                implementation(project(":core:di"))
                implementation(project(":core:serialization"))

                api(project(":feature:entities:core"))
            }
        }
    }
}
