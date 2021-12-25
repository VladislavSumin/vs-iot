package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName

@OptIn(KotlinPoetKspPreview::class)
class RSSubInterfaceProxyGenerator {

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
        return FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE, KModifier.SUSPEND)
            .returns(function.returnType!!.toTypeName())
            .addCode(
                // return processSuspend(interfaceName, functionName, typeOf<FunctionReturnType>())
                "return %M(%S, %S, %M<%T>())",
                processSuspend,
                interfaceName,
                function.simpleName.asString(),
                typeOf,
                function.returnType!!.toTypeName()
            )
            .build()
    }

    companion object {
        private val processSuspend = MemberName("", "processSuspend")
        private val typeOf = MemberName("kotlin.reflect", "typeOf")
    }
}
