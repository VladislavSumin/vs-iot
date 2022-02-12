// TODO убрать когда апи станет стабильным
@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }

        create("vsLibs") {
            from(files("../../vs-core/self-libs.versions.toml"))
        }
    }
}

includeBuild("../../vs-core/build-script")
