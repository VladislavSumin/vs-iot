plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
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
                implementation(project(":core:coroutines"))
                implementation(project(":core:decompose"))
                implementation(project(":core:di"))
                implementation(project(":core:ktor-client"))
                implementation(project(":core:logging"))
                implementation(project(":core:navigation2"))
                implementation(project(":core:uikit"))
                implementation(project(":core:utils"))

                implementation(libs.sqldelight.coroutines)

                implementation(project(":feature:servers:dto"))
            }
        }
    }
}
