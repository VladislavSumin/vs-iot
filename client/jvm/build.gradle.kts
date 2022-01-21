plugins {
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":client:common"))
            }
        }
    }
}
