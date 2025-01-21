import PackagePlugin

@main
struct ProtoTypeGeneratorPlugin: BuildToolPlugin {
    func createBuildCommands(
        context: PluginContext,
        target: Target
    ) async throws -> [Command] {
        // Locate the generator tool within the package
        let generatorTool = try context.tool(named: "GeneratorTool")

        // Configure paths for proto and JSON files
        let protoPath = target.directory.appending("example.proto")
        let jsonPath = target.directory.appending("instances.json")
        let outputPath = target.directory.appending("Generated")

        return [
            .prebuildCommand(
                displayName: "Generate Swift types from .proto",
                executable: generatorTool.path,
                arguments: [
                    "--proto-path", protoPath.string,
                    "--json-path", jsonPath.string,
                    "--output-path", outputPath.string
                ],
                environment: [:],
                outputFilesDirectory: outputPath
            )
        ]
    }
}
