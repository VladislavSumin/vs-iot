plugins {
    id("convention.android.library")
    id("com.squareup.sqldelight")
}

dependencies {
    implementation(project(":core:autostart"))
    implementation(project(":core:di"))
    api(project(":dto"))
    implementation(project(":feature:default-servers"))

    implementation(libs.kotlin.coroutines.core)

    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.android)

    implementation(libs.ktor.client.android)
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.servers.repository"
    }
}