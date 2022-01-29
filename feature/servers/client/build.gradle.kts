plugins {
    id("convention.preset.feature-client")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.servers.repository"
    }
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.sqldelight.coroutines)

                implementation(project(":feature:servers:dto"))
            }
        }
    }
}
