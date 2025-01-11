// swift-tools-version:5.7
import PackageDescription

let package = Package(
    name: "HelloWorld",
    products: [
        // Define the executable product
        .executable(
            name: "HelloWorld",
            targets: ["HelloWorld"]
        ),
        // Define the library product
        .library(
            name: "HelloLibrary",
            targets: ["HelloLibrary"]
        ),
    ],
    targets: [
        // Define the library target
        .target(
            name: "HelloLibrary",
            dependencies: [],
            path: "Swift/HelloLibrary"
        ),
        // Define the executable target that depends on the library
        .executableTarget(
            name: "HelloWorld",
            dependencies: ["HelloLibrary"],
            path: "Swift/HelloWorld"
        ),
    ]
)
