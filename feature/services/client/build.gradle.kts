plugins {
    id("convention.preset.feature-client")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:id"))

                implementation(project(":feature:servers:client"))
                api(project(":feature:entities:core"))
            }
        }
    }
}
