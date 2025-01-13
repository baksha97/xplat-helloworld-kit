import HelloKit
import CLib

let result = HelloKitLoader.loadRawHelloWorldJSON() ?? "An error occured getting the shared json."
print(result)

print(hello_world_kit_const)
