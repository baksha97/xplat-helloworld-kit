// buildSrc/src/main/kotlin/GenerateSealedClassTask.kt

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateSealedClassTask : DefaultTask() {
    @get:InputFile
    abstract val protoFile: RegularFileProperty

    @get:InputFile
    abstract val jsonFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        GenerateSealedClass(
            protoFile = protoFile.get().asFile,
            jsonFile = jsonFile.get().asFile,
            outputDir = outputDir.get().asFile
        ).generate()
    }
}