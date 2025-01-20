// buildSrc/src/main/kotlin/GenerateSealedClass.kt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.squareup.kotlinpoet.*
import java.io.File
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class GenerateSealedClass(
    private val protoFile: File,
    private val jsonFile: File,
    private val outputDir: File
) {
    private val mapper = jacksonObjectMapper()

    fun generate() {
        // Read and parse the proto file to get message name and package
        val protoContent = protoFile.readText()
        val messageName = extractMessageName(protoContent)
        val packageName = extractPackageName(protoContent)

        // Read the JSON file
        val jsonItems: List<Map<String, Any>> = mapper.readValue(jsonFile)

        // Generate the sealed class
        val fileSpec = generateKotlinFile(packageName, messageName, jsonItems)

        // Write the file
        fileSpec.writeTo(outputDir)
    }

    private fun extractMessageName(protoContent: String): String {
        // Simple regex to extract the first message name
        val messageRegex = """message\s+(\w+)\s+\{""".toRegex()
        return messageRegex.find(protoContent)?.groupValues?.get(1)
            ?: throw IllegalStateException("No message definition found in proto file")
    }

    private fun extractPackageName(protoContent: String): String {
        // Extract Java package from proto options
        val packageRegex = """option\s+java_package\s+=\s+"([^"]+)"""".toRegex()
        return packageRegex.find(protoContent)?.groupValues?.get(1)
            ?: throw IllegalStateException("No java_package option found in proto file")
    }

    private fun generateKotlinFile(
        packageName: String,
        messageName: String,
        items: List<Map<String, Any>>
    ): FileSpec {
        val className = "${messageName}Sealed"

        return FileSpec.builder(packageName, className)
            .addType(
                TypeSpec.classBuilder(className)
                    .addModifiers(KModifier.SEALED)
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("${messageName.lowercase()}", ClassName(packageName, messageName))
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("${messageName.lowercase()}", ClassName(packageName, messageName))
                            .initializer(messageName.lowercase())
                            .build()
                    )
                    .addTypes(generateDataObjects(packageName, messageName, items))
                    .addType(generateCompanionObject(messageName, items))
                    .build()
            )
            .build()
    }

    private fun generateDataObjects(
        packageName: String,
        messageName: String,
        items: List<Map<String, Any>>
    ): List<TypeSpec> {
        return items.map { item ->
            val objectName = (item["_name"] as String).capitalize()

            TypeSpec.objectBuilder(objectName)
                .addModifiers(KModifier.DATA)
                .superclass(ClassName(packageName, "${messageName}Sealed"))
                .addSuperclassConstructorParameter(
                    buildConstructorCall(messageName, item)
                )
                .build()
        }
    }

    private fun generateCompanionObject(
        messageName: String,
        items: List<Map<String, Any>>
    ): TypeSpec {
        val fromNameFunc = FunSpec.builder("fromName")
            .addParameter("name", String::class)
            .returns(ClassName("", "${messageName}Sealed"))
            .beginControlFlow("return when (name)")
            .apply {
                items.forEach { item ->
                    val name = item["_name"] as String
                    val objectName = name.capitalize()
                    addStatement("%S -> %L", name, objectName)
                }
                addStatement("else -> throw IllegalArgumentException(\"Unknown name: \$name\")")
            }
            .endControlFlow()
            .build()
        // Add the all property that returns a list of all objects
        val allProperty = PropertySpec.builder("all", LIST.parameterizedBy(ClassName("", "${messageName}Sealed")))
            .getter(
                FunSpec.getterBuilder()
                    .addStatement("return listOf(${
                        items.joinToString(", ") { item ->
                            item["_name"].toString().capitalize()
                        }
                    })")
                    .build()
            )
            .build()

        return TypeSpec.companionObjectBuilder()
            .addFunction(fromNameFunc)
            .addProperty(allProperty)
            .build()
    }

    private fun buildConstructorCall(messageName: String, item: Map<String, Any>): String {
        val params = item.entries
            .filter { it.key != "_name" }
            .joinToString("\n            .") { (key, value) ->
                val setter = "set${key.capitalize()}"
                when (value) {
                    is String -> "$setter(\"$value\")"
                    is Number -> "$setter($value)"
                    else -> "$setter($value)"
                }
            }

        return """
        |$messageName.newBuilder()
        |            .setName("${item["_name"]}")
        |            .$params
        |            .build()
    """.trimMargin()
    }
}

private fun String.capitalize() =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }