plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":client:common"))
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(project(":core:logging:slf4j"))
            }
        }
    }
}
