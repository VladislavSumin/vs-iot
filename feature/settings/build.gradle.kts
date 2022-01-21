plugins {
    id("convention.android.library")
    id("convention.android.compose")
}

dependencies {
    implementation(project(":core:compose"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))
}
