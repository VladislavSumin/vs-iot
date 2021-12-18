@file:OptIn(KotlinPoetKspPreview::class)

package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
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
            .primaryConstructor(generateConstructor(subscriptions))
            // .addProperties(generateRSubInterfacePropertiesImpl(client))
            // .addTypes(generateRSubInterfaceImpls(client))
            .build()

        val file = FileSpec.builder(subscriptions.packageName.asString(), "${subscriptions.simpleName.asString()}Impl")
            .addType(clazz)
            .build()

        file.writeTo(codeGenerator, false)
    }

    private fun generateConstructor(subscriptions: KSClassDeclaration): FunSpec {
        val annotation = subscriptions.annotations
            .first { it.annotationType.toTypeName() == RSubServerSubscriptions::class.asTypeName() }
        val impls = annotation.arguments.first().value!! as List<KSType>

        val params = impls.map {
            // TODO приводить к нижнему только первую букву
            ParameterSpec.builder(it.toClassName().simpleName.lowercase(), it.toClassName())
                .addModifiers(KModifier.PRIVATE)
                .build()
        }
        return FunSpec.constructorBuilder()
            .addParameters(params)
            .build()
    }
}
