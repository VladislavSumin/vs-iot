plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":rsub:core"))

                implementation(libs.ktor.http.cio)
            }
        }
    }
}
