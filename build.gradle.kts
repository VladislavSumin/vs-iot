buildscript {
    dependencies {
        classpath(libs.gradlePlugins.kotlin)
    }
}

plugins {
    id("convention.check-updates")
}

group = "org.example"
version = "1.0-SNAPSHOT"