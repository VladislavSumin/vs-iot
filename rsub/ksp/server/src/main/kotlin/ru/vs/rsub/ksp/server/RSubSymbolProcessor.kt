@file:OptIn(KotlinPoetKspPreview::class)

package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import ru.vs.rsub.RSubServerSubscriptions
import ru.vs.rsub.RSubServerSubscriptionsImpls

class RSubSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(RSubServerSubscriptions::class.qualifiedName!!)
            .forEach(this::processSubscriptions)
        return emptyList()
    }

    private fun processSubscriptions(subscriptions: KSAnnotated) {
        generateSubscriptions(subscriptions as KSClassDeclaration)
    }

    private fun generateSubscriptions(subscriptions: KSClassDeclaration) {
        val clazz = TypeSpec.classBuilder(subscriptions.simpleName.asString() + "Impl")
            .addOriginatingKSFile(subscriptions.containingFile!!)
            .addModifiers(KModifier.INTERNAL)
            .addSuperinterface(subscriptions.toClassName())
            .addSuperinterface(RSubServerSubscriptionsImpls::class)
            // .primaryConstructor(constructor)
            // .addProperties(generateRSubInterfacePropertiesImpl(client))
            // .addTypes(generateRSubInterfaceImpls(client))
            .build()

        val file = FileSpec.builder(subscriptions.packageName.asString(), "${subscriptions.simpleName.asString()}Impl")
            .addType(clazz)
            .build()

        file.writeTo(codeGenerator, false)
    }
}
