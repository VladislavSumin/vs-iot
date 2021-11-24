plugins {
    id("convention.android.library")
    id("com.squareup.sqldelight")
}

dependencies {
    implementation(project(":core:di"))

    implementation(libs.kotlin.coroutines.core)

    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.android)
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.servers.repository"
    }
}