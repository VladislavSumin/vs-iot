plugins {
    id("convention.multiplatform.jvm")
    id("convention.android.library-multiplatform")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.multiplatformSettings.core)
            }
        }
    }
}
