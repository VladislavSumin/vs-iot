package convention.android

plugins {
    id("kotlin-multiplatform")
    id("com.android.library")
    id("convention.android.base")
}

kotlin {
    android()
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}