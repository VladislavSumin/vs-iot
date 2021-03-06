plugins {
    id("ru.vs.convention.android.application")
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
    implementation(vsLibs.vs.core.utils)

    implementation(project(":client:common"))

    implementation(libs.android.core)

    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel.compose)
}
