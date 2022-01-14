plugins {
    id("convention.android.library-multiplatform")
    id("convention.multiplatform.jvm")
    id("org.jetbrains.compose")

//    id("convention.android.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
//                implementation(compose.desktop.currentOs)
            }
        }
        named("jvmMain") {
            dependencies {
                api(compose.desktop.currentOs)
            }
        }
        named("androidMain") {
            dependencies {
                api(libs.android.compose.ui)
                api(libs.android.compose.material)
                api(libs.android.compose.animation)
                api(libs.android.compose.uiToolingPreview)
                api(libs.android.activity.compose)
                api(libs.android.accompanist.swiperefresh)
            }
        }
        named("androidDebug") {
            dependencies {
                api(libs.android.compose.uiTooling)
            }
        }
    }
}
