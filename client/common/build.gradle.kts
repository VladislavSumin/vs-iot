plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("com.squareup.sqldelight")
}

evaluationDependsOn(":feature:servers")
sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.repository"
        dependency(project(":feature:servers:client"))
    }
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":core:coroutines"))
                api(project(":core:decompose"))
                api(project(":core:di"))
                api(project(":core:ktor-client"))
                api(project(":core:logging"))
                api(project(":core:navigation2"))
                api(project(":core:serialization"))
                api(project(":core:settings"))
                api(project(":core:uikit"))

                api(libs.sqldelight.coroutines)

                implementation(project(":feature:settings"))
                implementation(project(":feature:theming"))
                implementation(project(":feature:entities:client"))
                implementation(project(":feature:servers:client"))
                implementation(project(":feature:services:client"))
            }
        }

        named("androidMain") {
            dependencies {
                implementation(libs.sqldelight.android)
            }
        }

        named("jvmMain") {
            dependencies {
                implementation(libs.sqldelight.sqlite)
            }
        }
    }
}
