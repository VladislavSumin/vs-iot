plugins {
    id("convention.android.library")
    id("convention.android.compose")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.servers.repository"
    }
}

dependencies {
    implementation(project(":core:autostart"))
    implementation(project(":core:compose"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    implementation(project(":feature:servers:dto"))
    implementation(project(":feature:default-servers"))

    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.android)

    implementation(libs.ktor.client.android)
}
