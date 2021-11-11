// TODO убрать когда апи станет стабильным
@file:Suppress("UnstableApiUsage")

rootProject.name = "vs-iot"

include(":server")

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

pluginManagement {
    includeBuild("buildScript")
}