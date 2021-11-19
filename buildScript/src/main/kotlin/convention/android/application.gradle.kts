package convention.android

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.android.base")
}

android {
    // TODO перенести в base
    kotlinOptions {
        jvmTarget = "1.8"
    }
}