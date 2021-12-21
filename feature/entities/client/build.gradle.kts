plugins {
    id("convention.android.library")
    id("convention.android.compose")
}

dependencies {
    implementation(project(":core:autostart"))
    implementation(project(":core:compose"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:di"))
    implementation(project(":core:id"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    api(project(":feature:entities:core"))
    implementation(project(":feature:servers:client"))


    implementation(libs.ktor.client.android)
}