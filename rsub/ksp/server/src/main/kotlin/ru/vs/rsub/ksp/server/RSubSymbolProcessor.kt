@file:OptIn(KotlinPoetKspPreview::class)

package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
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
import ru.vs.rsub.RSubServerSubscriptionsAbstract

class RSubSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    private val subscriptionWrapperGenerator = RSubSubscriptionWrapperGenerator(logger)

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
            .superclass(RSubServerSubscriptionsAbstract::class)
            .addSuperinterface(subscriptions.toClassName())
            .primaryConstructor(generateConstructor(subscriptions))
            .addTypes(generateWrappers(subscriptions))
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
            ParameterSpec.builder(it.toClassName().simpleName.decapitalize(), it.toClassName())
                .build()
        }
        return FunSpec.constructorBuilder()
            .addParameters(params)
            .addCode(generateInitializer(subscriptions))
            .build()
    }

    private fun generateWrappers(subscriptions: KSClassDeclaration): List<TypeSpec> {
        val annotation = subscriptions.annotations
            .first { it.annotationType.toTypeName() == RSubServerSubscriptions::class.asTypeName() }
        val impls = annotation.arguments.first().value!! as List<KSType>

        return impls.map(subscriptionWrapperGenerator::generateSubscriptionWrapper)
    }

    private fun generateInitializer(subscriptions: KSClassDeclaration): CodeBlock {
        val annotation = subscriptions.annotations
            .first { it.annotationType.toTypeName() == RSubServerSubscriptions::class.asTypeName() }
        val impls = annotation.arguments.first().value!! as List<KSType>

        val builder = CodeBlock.builder()
        impls.forEach {
            builder.addStatement(
                "impls[%S] = %T(%L)",
                it.toClassName().simpleName,
                ClassName("", it.toClassName().simpleName + "Wrapper"),
                it.toClassName().simpleName.decapitalize(),
            )
        }
        return builder.build()
    }
}