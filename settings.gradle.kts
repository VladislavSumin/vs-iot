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
    ":core:coroutines",
    ":core:di",
    ":core:id",
    ":core:ktor-server",
    ":core:navigation",
    ":core:serialization",
    ":core:uikit",
    ":core:utils",
)

include(
    "rsub:client",
    "rsub:core",
    "rsub:playground",
    "rsub:server",
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
)

include(":server")
include(
    ":client:android",
//    ":client:jvm",
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