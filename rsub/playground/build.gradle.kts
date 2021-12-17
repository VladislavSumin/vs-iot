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

dependencies {
    implementation(project(":rsub:client"))
    ksp(project(":rsub:ksp:client"))
}