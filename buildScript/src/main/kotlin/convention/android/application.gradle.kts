package convention.android

plugins {
    id("com.android.application")
    kotlin("android")
    id("convention.android.base")
}

android {
    // TODO перенести в base
    kotlinOptions {
        jvmTarget = "1.8"
    }
}