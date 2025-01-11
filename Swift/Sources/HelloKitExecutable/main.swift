import HelloKit

let result = HelloKitLoader.loadRawHelloWorldJSON() ?? "An error occured getting the shared json."
print(result)
