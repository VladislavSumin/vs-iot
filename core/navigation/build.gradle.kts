plugins {
    id("convention.android.library")
    id("convention.android.compose")
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:utils"))
    implementation(libs.kodein.compose)
    api(libs.android.navigation.compose)
}