plugins {
    id("ru.vs.convention.multiplatform.jvm")
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
                implementation(libs.vs.coroutines)
                implementation(libs.vs.di)
                implementation(project(":core:ktor-server"))
                implementation(libs.vs.logging.slf4j)
                implementation(project(":core:serialization"))

                implementation(project(":feature:entities:server"))
                implementation(project(":feature:servers:dto"))
            }
        }
    }
}
