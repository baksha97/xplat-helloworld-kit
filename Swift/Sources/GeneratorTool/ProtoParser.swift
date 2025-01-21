import Foundation

struct Message {
    let name: String
    let fields: [Field]

    struct Field {
        let name: String
        let type: String
        let number: Int
    }
}

struct ProtoParser {
    func parse(protoPath: String) throws -> [Message] {
        let protoContent = try String(contentsOfFile: protoPath)
        // Simple regex-based parsing (improve as needed for complex schemas)
        var messages: [Message] = []

        let messageRegex = #"message (\w+) \{([\s\S]*?)\}"#
        let fieldRegex = #"(\w+) (\w+) = (\d+);"#

        let matches = try NSRegularExpression(pattern: messageRegex)
            .matches(in: protoContent, range: NSRange(protoContent.startIndex..., in: protoContent))

        for match in matches {
            let name = String(protoContent[Range(match.range(at: 1), in: protoContent)!])
            let body = String(protoContent[Range(match.range(at: 2), in: protoContent)!])

            var fields: [Message.Field] = []
            let fieldMatches = try NSRegularExpression(pattern: fieldRegex)
                .matches(in: body, range: NSRange(body.startIndex..., in: body))

            for fieldMatch in fieldMatches {
                let type = String(body[Range(fieldMatch.range(at: 1), in: body)!])
                let name = String(body[Range(fieldMatch.range(at: 2), in: body)!])
                let number = Int(body[Range(fieldMatch.range(at: 3), in: body)!])!
                fields.append(Message.Field(name: name, type: type, number: number))
            }

            messages.append(Message(name: name, fields: fields))
        }

        return messages
    }
}
