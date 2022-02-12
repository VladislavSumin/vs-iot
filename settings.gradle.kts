// TODO убрать когда апи станет стабильным
@file:Suppress("UnstableApiUsage")

pluginManagement {
    // /includeBuild("../vs-core/build-script")
    includeBuild("buildScript")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("com.gradle.enterprise") version ("3.7.1")
}

rootProject.name = "vs-iot"

includeBuild("../vs-core")

include(
    ":core:autostart",
    ":core:id",
    ":core:settings",
)

include(
    ":rsub:core",
    ":rsub:client",
    ":rsub:server",

    ":rsub:ksp:client",
    ":rsub:ksp:server",

    ":rsub:connector:ktor-websocket:core",
    ":rsub:connector:ktor-websocket:client",
    ":rsub:connector:ktor-websocket:server",

    ":rsub:playground",
    ":rsub:test",
)

include(
    ":feature:default-servers",

    ":feature:entities:client",
    ":feature:entities:core",
    ":feature:entities:dto",
    ":feature:entities:server",

    ":feature:servers:client",
    ":feature:servers:dto",

    ":feature:services:client",
    ":feature:services:server",

    ":feature:settings",

    ":feature:theming",
)

include(":server")
include(
    ":client:common",
    ":client:android",
    ":client:jvm",
)

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
        create("coreLibs") {
            from(files("../vs-core/core-libs.versions.toml"))
        }

        create("vsLibs") {
            from(files("../vs-core/self-libs.versions.toml"))
        }
    }
}

@Suppress("MagicNumber")
buildCache {
    local {
        removeUnusedEntriesAfterDays = 30
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
