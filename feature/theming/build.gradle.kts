plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("convention.multiplatform.resources")
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
                implementation(project(":core:navigation2"))
                implementation(project(":core:uikit"))
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
