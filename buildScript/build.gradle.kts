plugins {
    `kotlin-dsl`
}

dependencies {
    // TODO подождать пока эта фича появится в гредле
    // а пока костыль вот отсюда https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(files(vsLibs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.kotlinpoet.core)
    implementation("ru.vs:build-script:0.1.0")
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
