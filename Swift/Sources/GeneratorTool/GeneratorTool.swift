import Foundation
import ArgumentParser
//import SourceKittenFramework

@main
struct GeneratorTool: ParsableCommand {
  //    @Option(name: .long, help: "Path to the .proto file")
  //    var protoPath: String
  //
  //    @Option(name: .long, help: "Path to the JSON configuration file")
  //    var jsonPath: String
  //
  //    @Option(name: .long, help: "Output directory for generated Swift files")
  //    var outputPath: String
  
  func run() throws {
    let protoParser = ProtoParser()
    let jsonProcessor = JSONProcessor()
    let codeGenerator = CodeGenerator()
//    let files = try deepSearch(URL(fileURLWithPath: input, isDirectory: true))
    // Step 1: Parse .proto schema
    //    guard let proto =  else {
    //      fatalError("missing proto")
    //    }
    let messages = try protoParser.parse(protoPath: "hello.proto")
    
    // Step 2: Validate and process JSON instances
    let instances = try jsonProcessor.process(jsonPath: "hello_world.json", schema: messages)
    
    // Step 3: Generate Swift code
    try codeGenerator.generate(messages: messages, instances: instances, outputPath: "./Generated")
  }
}
