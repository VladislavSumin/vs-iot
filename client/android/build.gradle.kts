plugins {
    id("convention.android.application")
    id("convention.android.compose")
    kotlin("kapt")
}

android {
    defaultConfig {
        applicationId = "ru.vs.iot"
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:autostart"))
    implementation(project(":core:compose"))
    implementation(project(":core:navigation"))
    implementation(project(":core:utils"))

    implementation(project(":client:common"))

    implementation(project(":feature:entities:client"))
    implementation(project(":feature:services:client"))

    implementation(libs.android.core)

    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel.compose)
}
