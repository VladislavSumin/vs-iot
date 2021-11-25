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
    api(libs.android.compose.ui)
    api(libs.android.compose.material)
    api(libs.android.compose.animation)
    api(libs.android.compose.uiToolingPreview)
    api(libs.android.activity.compose)
    api(libs.android.accompanist.swiperefresh)

    debugApi(libs.android.compose.uiTooling)
}