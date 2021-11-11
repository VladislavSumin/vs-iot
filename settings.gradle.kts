rootProject.name = "vs-iot"

include(":server")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    includeBuild("buildScript")
}