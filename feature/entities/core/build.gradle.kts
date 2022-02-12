plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(vsLibs.vs.core.di)
                implementation(vsLibs.vs.core.serialization)

                api(project(":feature:entities:dto"))
            }
        }
    }
}
