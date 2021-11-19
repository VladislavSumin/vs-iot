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

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// For correct caching :buildScript:generatePrecompiledScriptPluginAccessors
// https://github.com/gradle/gradle/issues/15214
System.setProperty("org.gradle.kotlin.dsl.precompiled.accessors.strict", "true")

gradlePlugin {
    plugins {
        create("DefaultServersPlugin") {
            id = "default_servers"
            implementationClass = "ru.vs.iot.build_script.default_servers.DefaultServersPlugin"
        }
    }
}
