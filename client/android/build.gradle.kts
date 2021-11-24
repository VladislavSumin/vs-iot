plugins {
    id("convention.android.application")
    kotlin("kapt")
    id("com.squareup.sqldelight")
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.repository"
        dependency(project(":feature:servers"))
    }
}

dependencies {
    implementation(project(":core:autostart"))
    implementation(project(":core:di"))
    implementation(project(":core:id"))
    implementation(project(":core:navigation"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    implementation(project(":dto"))

    implementation(project(":feature:servers"))

    implementation(libs.kodein.compose)

    implementation(libs.kotlin.coroutines.core)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)

    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.android)

    implementation(libs.android.core)

    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel.compose)
    //implementation(libs.android.accompanist.navigation.animation)
    implementation(libs.android.accompanist.swiperefresh)


//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
}
