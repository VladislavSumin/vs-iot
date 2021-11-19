plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradlePlugins.kotlin)
    implementation(libs.gradlePlugins.android)
    implementation(libs.gradlePlugins.checkUpdates)
    implementation(libs.gradlePlugins.detekt)
    implementation(libs.kotlinpoet)
}

gradlePlugin {
    plugins {
        create("DefaultServersPlugin") {
            id = "default_servers"
            implementationClass = "ru.vs.iot.build_script.default_servers.DefaultServersPlugin"
        }
    }
}
