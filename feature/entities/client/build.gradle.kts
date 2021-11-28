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
    implementation(project(":core:autostart"))
    implementation(project(":core:compose"))
    implementation(project(":core:di"))
    implementation(project(":core:id"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    api(project(":feature:entities:core"))
    implementation(project(":feature:servers:client"))

    implementation(libs.kotlin.coroutines.core)

    implementation(libs.ktor.client.android)
}