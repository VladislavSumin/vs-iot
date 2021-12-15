plugins {
    id("convention.android.library")
    id("default_servers")
}

val pIsBuildAgent: String by project
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
    implementation(project(":core:di"))
    implementation(project(":core:coroutines"))

    implementation(libs.android.datastore.android)
    implementation(libs.android.datastore.preferences)
}