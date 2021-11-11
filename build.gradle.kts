buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

plugins {
    id("convention.check-updates")
}

group = "org.example"
version = "1.0-SNAPSHOT"