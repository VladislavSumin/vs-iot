plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.squareup.sqldelight")
    id("default_servers")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "ru.vs.iot"
        minSdk = 30
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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
    }
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":dto"))

    implementation(libs.kodein.compose)

    implementation(libs.kotlin.coroutines.core)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)

    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.android)

    implementation(libs.android.core)
    implementation(libs.android.compose.ui)
    implementation(libs.android.compose.material)
    implementation(libs.android.compose.animation)
    implementation(libs.android.compose.uiToolingPreview)
    implementation(libs.android.activity.compose)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel.compose)
    implementation(libs.android.navigation.compose)
    implementation(libs.android.accompanist.navigation.animation)

    debugImplementation(libs.android.compose.uiTooling)

//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
}