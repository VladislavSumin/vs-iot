package convention

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    autoCorrect = true
    parallel = true
    buildUponDefaultConfig = true
    config = files("${project.rootProject.projectDir}/config/detekt/detekt.yml")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detekt.toolVersion}")
}