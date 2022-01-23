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
                api(project(":core:coroutines"))
                api(project(":core:decompose"))
                api(project(":core:di"))
                api(project(":core:logging"))
                api(project(":core:navigation2"))
                api(project(":core:uikit"))

                implementation(project(":feature:settings"))
                implementation(project(":feature:theming"))
            }
        }
    }
}
