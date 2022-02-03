plugins {
    id("convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(libs.vs.coroutines)
                implementation(project(":core:di"))
                implementation(project(":core:ktor-server"))
                implementation(project(":core:serialization"))

                api(project(":feature:entities:core"))
            }
        }
    }
}
