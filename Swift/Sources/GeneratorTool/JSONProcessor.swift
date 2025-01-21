import Foundation

struct GeneratorError: Error {
  
}

struct Instance {
    let name: String
    let values: [String: Any]
}

struct JSONProcessor {
    func process(jsonPath: String, schema: [Message]) throws -> [Instance] {
        let jsonData = try Data(contentsOf: URL(fileURLWithPath: jsonPath))
        let jsonObjects = try JSONSerialization.jsonObject(with: jsonData, options: []) as! [[String: Any]]

        var instances: [Instance] = []
        for object in jsonObjects {
            guard let name = object["_name"] as? String else {
                throw GeneratorError()
            }
            instances.append(Instance(name: name, values: object))
        }

        return instances
    }
}
