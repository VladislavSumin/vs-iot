package ru.vs.rsub.ksp.server

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlinx.coroutines.flow.Flow
import ru.vs.rsub.RSubServerSubscription
import ru.vs.rsub.RSubServerSubscriptionsAbstract

@OptIn(KotlinPoetKspPreview::class)
class RSubSubscriptionWrapperGenerator(
    private val logger: KSPLogger
) {
    fun generateWrappers(impls: List<KSType>): List<TypeSpec> {
        return impls.map(this::generateWrapper)
    }

    private fun generateWrapper(impl: KSType): TypeSpec {
        return TypeSpec.classBuilder(impl.toClassName().simpleName + NAME_POSTFIX)
            .addModifiers(KModifier.PRIVATE)
            .primaryConstructor(generateConstructor(impl))
            .superclass(RSubServerSubscriptionsAbstract.InterfaceWrapperAbstract::class)
            .build()
    }

    private fun generateConstructor(impl: KSType): FunSpec {
        return FunSpec.constructorBuilder()
            .addParameter(PARAM_NAME, impl.toTypeName())
            .addCode(generateInitializers(impl))
            .build()
    }

    private fun generateInitializers(impl: KSType): CodeBlock {
        val builder = CodeBlock.builder()
        (impl.declaration as KSClassDeclaration)
            .getAllFunctions()
            .filter { it.isAbstract }
            .forEach { builder.generateInitializer(it) }
        return builder.build()
    }

    private fun CodeBlock.Builder.generateInitializer(method: KSFunctionDeclaration) {
        when {
            method.modifiers.contains(Modifier.SUSPEND) -> generateInitializerTyped(createSuspend, method)
            method.returnType!!.resolve().toClassName() == flowClassName -> generateInitializerTyped(createFlow, method)
            else -> {
                logger.error("Cannot generate wrapper for this function", method)
            }
        }
    }

    private fun CodeBlock.Builder.generateInitializerTyped(wrapperFunction: MemberName, method: KSFunctionDeclaration) {
        val methodName = method.simpleName.asString()
        addStatement("%M[%S] = %M(%L::%L)", methodImpls, methodName, wrapperFunction, PARAM_NAME, methodName)
    }

    companion object {
        private const val NAME_POSTFIX = "Wrapper"
        private const val PARAM_NAME = "impl"
        private val createSuspend = RSubServerSubscription::class.asClassName()
            .nestedClass("Companion")
            .member("createSuspend")
        private val createFlow = RSubServerSubscription::class.asClassName()
            .nestedClass("Companion")
            .member("createFlow")
        private val methodImpls = MemberName("", "methodImpls")
        private val flowClassName = Flow::class.asClassName()
    }
}
