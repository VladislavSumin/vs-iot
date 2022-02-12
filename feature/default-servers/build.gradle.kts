plugins {
    id("ru.vs.convention.android.library")
    id("default_servers")
}

val pIsBuildAgent: String by project
@Suppress("MagicNumber")
android {
    buildTypes {
        debug {
            defaultServers {
                if (!pIsBuildAgent.toBoolean()) {
                    createLocalMachineDevServer()
                    createEmptyServersTemplate(20)
                }
            }
        }
    }
}

dependencies {
    implementation(vsLibs.vs.core.coroutines)
    implementation(vsLibs.vs.core.di)

    implementation(libs.android.datastore.android)
    implementation(libs.android.datastore.preferences)
}
