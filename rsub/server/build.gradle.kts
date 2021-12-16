plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":rsub:core"))
            }
        }
    }
}
