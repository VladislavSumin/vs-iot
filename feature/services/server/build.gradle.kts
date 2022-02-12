plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("ru.vs.convention.multiplatform.serialization")
}

kotlin {
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(vsLibs.vs.core.coroutines)
                implementation(vsLibs.vs.core.di)
                implementation(vsLibs.vs.core.serialization)
            }
        }
    }
}
