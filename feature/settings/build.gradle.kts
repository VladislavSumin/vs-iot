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
                implementation(project(":core:decompose"))
                implementation(project(":core:navigation"))
                implementation(project(":core:uikit"))

                implementation(project(":feature:theming"))
            }
        }
    }
}

// dependencies {
//    implementation(project(":core:compose"))
//    implementation(project(":core:coroutines"))
//    implementation(project(":core:di"))
//    implementation(project(":core:navigation"))
//    implementation(project(":core:uikit"))
//    implementation(project(":core:utils"))
// }
