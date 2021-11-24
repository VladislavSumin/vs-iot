plugins {
    id("convention.android.library")
    id("com.squareup.sqldelight")
}

dependencies {
    implementation(project(":core:di"))
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.servers.repository"
    }
}