plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.serialization")

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
                implementation(vsLibs.vs.core.coroutines)
                implementation(vsLibs.vs.core.di)
                implementation(vsLibs.vs.core.ktor.server)
                implementation(vsLibs.vs.core.logging.slf4j)
                implementation(vsLibs.vs.core.serialization)

                implementation(project(":feature:entities:server"))
                implementation(project(":feature:servers:dto"))
            }
        }
    }
}
