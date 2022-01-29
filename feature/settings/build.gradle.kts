plugins {
    id("convention.preset.feature-client")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":feature:theming"))
            }
        }
    }
}
