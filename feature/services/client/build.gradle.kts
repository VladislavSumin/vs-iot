plugins {
    id("convention.android.library")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.get()
    }
}

dependencies {
    implementation(project(":core:compose"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    implementation(libs.ktor.client.android)
}