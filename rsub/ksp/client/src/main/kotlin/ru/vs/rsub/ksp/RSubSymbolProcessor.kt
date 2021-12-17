@file:OptIn(KotlinPoetKspPreview::class)

package ru.vs.rsub.ksp

import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import ru.vs.rsub.RSubClient
import ru.vs.rsub.RSubClientAbstract
import ru.vs.rsub.RSubConnector
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class RSubSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(RSubClient::class.qualifiedName!!)
            .forEach(this::processRSubClients)

        return emptyList()
    }

    private fun processRSubClients(client: KSAnnotated) {
        check(client is KSClassDeclaration, client, "Only class declaration supported")
        check(client.classKind == ClassKind.INTERFACE, client, "Only interfaces supported")
        generateRSubClientImpl(client)
    }

    private fun generateRSubClientImpl(client: KSClassDeclaration) {

        val constructor = FunSpec.constructorBuilder()
            .addParameter("connector", RSubConnector::class)
            .build()

        val clazz = TypeSpec.classBuilder(client.simpleName.asString() + "Impl")
            .addOriginatingKSFile(client.containingFile!!)
            .addModifiers(KModifier.INTERNAL)
            .superclass(RSubClientAbstract::class)
            .addSuperinterface(client.toClassName())
            .primaryConstructor(constructor)
            .addSuperclassConstructorParameter("connector")
            .addProperties(generateRSubInterfacePropertiesImpl(client))
            .addTypes(generateRSubInterfaceImpls(client))
            .build()

//        val operator = FunSpec.builder("invoke")
//            .receiver(client.toClassName())
//            .returns(client.toClassName())
//            .addModifiers(KModifier.OPERATOR, KModifier.INTERNAL)
//            .addStatement("return  ${clazz.name!!}()")
//            .build()

        val file = FileSpec.builder(client.packageName.asString(), "${client.simpleName.asString()}Impl")
            .addType(clazz)
            .build()

        file.writeTo(codeGenerator, false)
    }

    private fun generateRSubInterfacePropertiesImpl(client: KSClassDeclaration) =
        client.getAllProperties()
            .filter { it.isAbstract() }
            .map {
                val superInterface = it.type.resolve().declaration as KSClassDeclaration
                PropertySpec.builder(
                    it.simpleName.asString(),
                    superInterface.toClassName(),
                    KModifier.OVERRIDE
                )
                    .initializer("${superInterface.simpleName.asString()}Impl()")
                    .build()
            }
            .asIterable()

    private fun generateRSubInterfaceImpls(client: KSClassDeclaration): Iterable<TypeSpec> =
        client.getAllProperties()
            .filter { it.isAbstract() }
            .map {
                val superInterface = it.type.resolve().declaration as KSClassDeclaration
                TypeSpec.classBuilder("${superInterface.simpleName.asString()}Impl")
                    .addSuperinterface(superInterface.toClassName())
                    .addModifiers(KModifier.INNER, KModifier.PRIVATE)
                    .build()
            }
            .asIterable()

    @OptIn(ExperimentalContracts::class)
    private fun check(value: Boolean, node: KSNode, message: String) {
        contract {
            returns() implies value
        }
        if (!value) {
            logger.error(message, node)
            throw IllegalStateException(message)
        }
    }
}
