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
                api(vsLibs.vs.core.coroutines)
                api(vsLibs.vs.core.di)
                api(vsLibs.vs.core.decompose)
                api(vsLibs.vs.core.uikit)
                api(vsLibs.vs.core.navigation)
                api(vsLibs.vs.core.ktor.client)
                api(vsLibs.vs.core.logging.core)
                api(vsLibs.vs.core.serialization)
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
