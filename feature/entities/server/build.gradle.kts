plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(libs.vs.coroutines)
                implementation(libs.vs.di)

                implementation(vsLibs.vs.core.ktor.server)
                implementation(vsLibs.vs.core.serialization)

                api(project(":feature:entities:core"))
            }
        }
    }
}
