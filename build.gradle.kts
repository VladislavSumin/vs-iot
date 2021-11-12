buildscript {
    dependencies {
        classpath(libs.gradlePlugins.kotlin)
        classpath(libs.gradlePlugins.kotlin.serialization)
        classpath(libs.gradlePlugins.android)
        classpath(libs.gradlePlugins.buildConfig)
    }
}

plugins {
    id("convention.check-updates")
}

group = "org.example"
version = "1.0-SNAPSHOT"