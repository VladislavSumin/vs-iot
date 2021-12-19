package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.vs.rsub.RSubServerSubscription
import ru.vs.rsub.RSubServerSubscriptionsAbstract

@OptIn(KotlinPoetKspPreview::class)
@Suppress("UnusedPrivateMember")
class RSubSubscriptionWrapperGenerator(
    private val logger: KSPLogger,
) {
    fun generateSubscriptionWrapper(impl: KSType): TypeSpec {
        val constructor = FunSpec.constructorBuilder()
            .addParameter(impl.toClassName().simpleName.decapitalize(), impl.toTypeName())
            .addCode(generateInitializer(impl))
            .build()
        return TypeSpec.classBuilder(impl.toClassName().simpleName + "Wrapper")
            .addModifiers(KModifier.PRIVATE)
            .primaryConstructor(constructor)
            .superclass(RSubServerSubscriptionsAbstract.InterfaceWrapperAbstract::class)
            .build()
    }

    private fun generateInitializer(impl: KSType): CodeBlock {
        val builder = CodeBlock.builder()
        (impl.declaration as KSClassDeclaration)
            .getAllFunctions()
            .filter { it.isAbstract }
            .forEach {
                builder.addStatement(
                    "methodImpls[%S] = %T { %L.%L() }",
                    it.simpleName.asString(),
                    RSubServerSubscription.SuspendSub::class,
                    impl.toClassName().simpleName.decapitalize(),
                    it.simpleName.asString()
                )
            }
        return builder.build()
    }
}
