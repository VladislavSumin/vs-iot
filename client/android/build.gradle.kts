plugins {
    id("convention.android.application")
    id("convention.android.compose")
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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

evaluationDependsOn(":feature:servers")
sqldelight {
    database("Database") {
        packageName = "ru.vs.iot.repository"
        dependency(project(":feature:servers:client"))
    }
}

dependencies {
    implementation(project(":core:autostart"))
    implementation(project(":core:compose"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:serialization"))
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    implementation(project(":feature:entities:client"))
    implementation(project(":feature:servers:client"))
    implementation(project(":feature:services:client"))
    implementation(project(":feature:settings"))

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)

    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.android)

    implementation(libs.android.core)

    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel.compose)
    //implementation(libs.android.accompanist.navigation.animation)


//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
}
