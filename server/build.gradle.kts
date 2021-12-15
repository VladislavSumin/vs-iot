plugins {
    id("convention.multiplatform.jvm")
    id("convention.multiplatform.serialization")

    id("com.github.gmazzo.buildconfig")
}

val version: String by project
buildConfig {
    className("BuildConfig")
    packageName("ru.vs.iot.server")
    buildConfigField("String", "version", "\"$version\"")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":core:coroutines"))
                implementation(project(":core:di"))
                implementation(project(":core:ktor-server"))
                implementation(project(":core:serialization"))

                implementation(project(":feature:entities:server"))
                implementation(project(":feature:servers:dto"))

                implementation(libs.logging.kermit)
                implementation(libs.logging.log4j.api)
                implementation(libs.logging.log4j.core)
                implementation(libs.logging.log4j.slf4j)
                implementation(libs.logging.slf4j)
            }
        }
    }
}
