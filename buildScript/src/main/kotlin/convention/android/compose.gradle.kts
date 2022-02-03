package convention.android

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the
import ru.vs.build_script.utils.android

val libs = the<LibrariesForLibs>()

android {
    buildFeatures.apply {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.composeCompiler.get()
    }
}
