package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlinx.coroutines.flow.Flow

@OptIn(KotlinPoetKspPreview::class)
class RSSubInterfaceProxyGenerator(
    private val logger: KSPLogger
) {

    fun TypeSpec.Builder.generateProxyClassesWithProxyInstances(
        superProperties: Sequence<KSPropertyDeclaration>
    ): TypeSpec.Builder {
        superProperties.forEach { generateProxyClassWithProxyInstance(it) }
        return this
    }

    private fun TypeSpec.Builder.generateProxyClassWithProxyInstance(superProperty: KSPropertyDeclaration) {
        val superInterface = superProperty.type.resolve().declaration as KSClassDeclaration
        addProperty(generateProxyHolder(superProperty, superInterface))
        addType(generateProxyClass(superInterface))
    }

    private fun generateProxyClass(superInterface: KSClassDeclaration): TypeSpec {
        return TypeSpec.classBuilder("${superInterface.simpleName.asString()}Impl")
            .addModifiers(KModifier.PRIVATE, KModifier.INNER)
            .addSuperinterface(superInterface.toClassName())
            .addFunctions(generateProxyFunctions(superInterface))
            .build()
    }

    private fun generateProxyHolder(
        superProperty: KSPropertyDeclaration,
        superInterface: KSClassDeclaration
    ): PropertySpec {
        return PropertySpec.builder(
            superProperty.simpleName.asString(),
            superInterface.toClassName(),
            KModifier.OVERRIDE
        )
            .initializer("${superInterface.simpleName.asString()}Impl()")
            .build()
    }

    private fun generateProxyFunctions(baseInterface: KSClassDeclaration): Iterable<FunSpec> {
        val interfaceName = baseInterface.simpleName.asString()
        return baseInterface.getAllFunctions()
            .filter { it.isAbstract }
            .map { generateProxyFunction(interfaceName, it) }
            .asIterable()
    }

    private fun generateProxyFunction(interfaceName: String, function: KSFunctionDeclaration): FunSpec {
        return when {
            function.modifiers.contains(Modifier.SUSPEND) -> {
                generateSuspendProxyFunction(interfaceName, function)
            }
            function.returnType!!.resolve().toClassName() == Flow::class.asClassName() -> {
                generateFlowProxyFunction(interfaceName, function)
            }
            else -> {
                logger.error("Cannot generate wrapper for this function", function)
                error("Cannot generate wrapper for this function")
            }
        }
    }

    private fun generateSuspendProxyFunction(interfaceName: String, function: KSFunctionDeclaration): FunSpec {
        return FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE, KModifier.SUSPEND)
            .returns(function.returnType!!.toTypeName())
            .addCode(
                "return %M(%S, %S)",
                processSuspend,
                interfaceName,
                function.simpleName.asString()
            )
            .build()
    }

    private fun generateFlowProxyFunction(interfaceName: String, function: KSFunctionDeclaration): FunSpec {
        return FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE)
            .returns(function.returnType!!.toTypeName())
            .addCode(
                "return %M(%S, %S)",
                processFlow,
                interfaceName,
                function.simpleName.asString()
            )
            .build()
    }

    companion object {
        private val processSuspend = MemberName("", "processSuspend")
        private val processFlow = MemberName("", "processFlow")
    }
}
