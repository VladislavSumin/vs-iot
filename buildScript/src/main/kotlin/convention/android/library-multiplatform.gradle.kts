package convention.android

plugins {
    id("com.android.library")
    id("convention.android.base")
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}