package ru.vs.iot.build_script.utils

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.FeatureExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

internal fun Project.android(): BaseExtension = extensions.getByType()
internal fun Project.android(block: BaseExtension.() -> Unit) = android().block()

internal fun BaseExtension.variants(): DomainObjectSet<out BaseVariant> {
    return when (this) {
        is AppExtension -> applicationVariants
        is LibraryExtension -> libraryVariants
        else -> throw GradleException("Unsupported BaseExtension type!")
    }
}

internal fun BaseExtension.variants(block: DomainObjectSet<out BaseVariant>.() -> Unit) = variants().block()
