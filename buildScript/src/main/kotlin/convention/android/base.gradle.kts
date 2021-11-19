package convention.android

import ru.vs.iot.build_script.utils.android
import ru.vs.iot.build_script.utils.kotlinOptions

plugins {
    kotlin("android")
}

android {
    setCompileSdkVersion(31)

    defaultConfig {
        minSdk = 30
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}