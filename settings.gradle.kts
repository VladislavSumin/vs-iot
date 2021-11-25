// TODO убрать когда апи станет стабильным
@file:Suppress("UnstableApiUsage")

pluginManagement {
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

include(
    ":core:autostart",
    ":core:compose",
    ":core:di",
    ":core:id",
    ":core:navigation",
    ":core:uikit",
    ":core:utils",
)
include(
    ":feature:default-servers",
    ":feature:servers:client",
    ":feature:servers:dto",
    ":feature:settings",
)
include(":dto")
include(":server")
include(":client:android")

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
    }
}

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