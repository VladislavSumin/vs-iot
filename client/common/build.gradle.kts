plugins {
    id("ru.vs.convention.multiplatform.android-library")
    id("ru.vs.convention.multiplatform.jvm")
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
                api(libs.vs.coroutines)
                api(libs.vs.di)
                api(libs.vs.decompose)
                api(libs.vs.uikit)
                api(libs.vs.navigation)
                api(project(":core:ktor-client"))
                api(libs.vs.logging.core)
                api(project(":core:serialization"))
                api(project(":core:settings"))

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
