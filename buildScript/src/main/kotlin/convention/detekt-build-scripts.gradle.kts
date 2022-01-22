package convention

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("convention.detekt")
}

val detektBuildScript = tasks.register<Detekt>("detektBuildScript") {
    source = fileTree(project.rootDir).matching {
        include("buildScript/src/**/*.kt", "**/*.gradle.kts")
        exclude("**/build/**")
    }
}

tasks.named("detekt").configure { dependsOn(detektBuildScript) }
