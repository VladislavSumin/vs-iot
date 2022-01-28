plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.servers.repository"
    }
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":core:coroutines"))
                api(project(":core:decompose"))
                implementation(project(":core:di"))
//                api(project(":core:logging"))
                api(project(":core:navigation2"))
//                api(project(":core:settings"))
                implementation(project(":core:uikit"))
                implementation(project(":core:utils"))

                implementation(libs.ktor.client.core)
                implementation(libs.sqldelight.coroutines)

                implementation(project(":feature:servers:dto"))
            }
        }
    }
}

// dependencies {
//    // implementation(project(":core:autostart"))
//    implementation(project(":core:compose"))
//    implementation(project(":core:coroutines"))
//    implementation(project(":core:di"))
//    implementation(project(":core:navigation"))
//    implementation(project(":core:uikit"))
//    implementation(project(":core:utils"))
//
//    implementation(project(":feature:servers:dto"))
//    // implementation(project(":feature:default-servers"))
//
//    implementation(libs.sqldelight.coroutines)
//    implementation(libs.sqldelight.android)
//
//    implementation(libs.ktor.client.android)
// }
