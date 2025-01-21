import Foundation

struct CodeGenerator {
    func generate(messages: [Message], instances: [Instance], outputPath: String) throws {
        for message in messages {
            var output = "enum \(message.name)Type {\n"

            for instance in instances {
                output += "    case \(instance.name)\n"
            }

            output += "\n    var model: \(message.name) {\n"
            output += "        switch self {\n"

            for instance in instances {
                output += "        case .\(instance.name):\n"
                output += "            return \(message.name)(\(instance.values.map { "\($0): \($1)" }.joined(separator: ", ")))\n"
            }

            output += "        }\n"
            output += "    }\n"
            output += "}\n"

            let outputFilePath = "\(outputPath)/\(message.name)Type.swift"
            try FileManager.default.createDirectory(atPath: outputPath, withIntermediateDirectories: true)
            try output.write(toFile: outputFilePath, atomically: true, encoding: .utf8)
        }
    }
}
