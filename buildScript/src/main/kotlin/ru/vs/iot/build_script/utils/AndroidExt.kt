package ru.vs.iot.build_script.utils

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal val Project.android: BaseExtension
    get() = extensions.getByType()

internal fun Project.android(block: BaseExtension.() -> Unit) = android.block()

internal val BaseExtension.variants: DomainObjectSet<out BaseVariant>
    get() = when (this) {
        is AppExtension -> applicationVariants
        is LibraryExtension -> libraryVariants
        else -> throw GradleException("Unsupported BaseExtension type!")
    }

internal fun BaseExtension.variants(block: DomainObjectSet<out BaseVariant>.() -> Unit) = variants.block()

internal val BaseExtension.kotlinOptions: KotlinJvmOptions
    get() = (this as ExtensionAware).extensions.getByType()

internal fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) = kotlinOptions.block()
