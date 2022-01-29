package convention.preset

plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("convention.multiplatform.resources")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:compose"))
                implementation(project(":core:coroutines"))
                implementation(project(":core:decompose"))
                implementation(project(":core:di"))
                implementation(project(":core:ktor-client"))
                implementation(project(":core:logging"))
                implementation(project(":core:navigation"))
                implementation(project(":core:serialization"))
                implementation(project(":core:settings"))
                implementation(project(":core:uikit"))
                implementation(project(":core:utils"))
            }
        }
    }
}
