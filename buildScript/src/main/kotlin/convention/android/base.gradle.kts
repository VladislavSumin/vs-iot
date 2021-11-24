package convention.android

import ru.vs.iot.build_script.utils.android
import ru.vs.iot.build_script.utils.kotlinOptions

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
}