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
    implementation(project(":core:di"))
}