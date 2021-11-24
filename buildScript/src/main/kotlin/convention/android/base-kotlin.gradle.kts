package convention.android

import ru.vs.iot.build_script.utils.android
import ru.vs.iot.build_script.utils.kotlinOptions

plugins {
    id("convention.android.base")
    kotlin("android")
}

android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}