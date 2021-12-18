plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

sourceSets {
    main {
        java {
            srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":rsub:client"))
    ksp(project(":rsub:ksp:client"))

    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.testing.turbine)

    testImplementation(libs.testing.jupiter.api)
    testRuntimeOnly(libs.testing.jupiter.engine)
    testRuntimeOnly(libs.testing.jupiter.params)
    testRuntimeOnly(libs.testing.jupiter.platformSuite)

    testImplementation(libs.testing.mockito.core)
    testImplementation(libs.testing.mockito.jupiter)
    testImplementation(libs.testing.mockito.kotlin)
}