plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:coroutines"))
                implementation(project(":core:decompose"))
                implementation(project(":core:di"))
                implementation(project(":core:id"))
                implementation(project(":core:ktor-client"))
                implementation(project(":core:logging"))
                implementation(project(":core:navigation2"))
                implementation(project(":core:uikit"))
                implementation(project(":core:utils"))

                implementation(project(":feature:servers:client"))
                api(project(":feature:entities:core"))
            }
        }
    }
}
