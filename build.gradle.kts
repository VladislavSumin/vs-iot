import ru.vs.iot.build_script.utils.namedOrNull

buildscript {
    dependencies {
        classpath(libs.gradlePlugins.sqldelight)
        classpath(libs.gradlePlugins.buildConfig)
        classpath(libs.gradlePlugins.ksp)
    }
}

plugins {
    id("convention.check-updates")
    id("convention.detekt-build-scripts")
}

allprojects {
    apply {
        plugin("convention.detekt")
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

tasks.register("ci") {
    // Client
    dependsOn(":client:android:assemble")
    dependsOn(":client:jvm:assemble")

    // Server
    dependsOn(":server:assemble")

    // RSub
    dependsOn(":rsub:playground:assemble")
    dependsOn(":rsub:test:test")

    // Other
    dependsOn(":dependencyUpdates")
}

tasks.register("preCommit") {
    allprojects {
        val detekt = tasks.namedOrNull("detekt")
        if (detekt != null) dependsOn(detekt)
    }
}
