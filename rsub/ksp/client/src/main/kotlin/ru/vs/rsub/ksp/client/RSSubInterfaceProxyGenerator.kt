package ru.vs.rsub.ksp.client

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName

@OptIn(KotlinPoetKspPreview::class)
class RSSubInterfaceProxyGenerator {

    fun generateProxyClass(superInterface: KSClassDeclaration): TypeSpec {
        return TypeSpec.classBuilder("${superInterface.simpleName.asString()}Impl")
            .addModifiers(KModifier.PRIVATE, KModifier.INNER)
            .addSuperinterface(superInterface.toClassName())
            .addFunctions(generateProxyFunctions(superInterface))
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
