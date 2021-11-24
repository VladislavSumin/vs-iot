import ru.vs.iot.build_script.utils.namedOrNull

buildscript {
    dependencies {
        classpath(libs.gradlePlugins.kotlin.serialization)
        classpath(libs.gradlePlugins.sqldelight)
        classpath(libs.gradlePlugins.buildConfig)
        classpath(libs.gradlePlugins.jb.compose)
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
    dependsOn(":client:android:assemble")
    dependsOn(":server:assemble")
    dependsOn(":dependencyUpdates")
}

tasks.register("preCommit") {
    allprojects {
        val detekt = tasks.namedOrNull("detekt")
        if (detekt != null) dependsOn(detekt)
    }
}