// swift-tools-version:6.0
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
  name: "HelloKit",
  platforms: [.macOS(.v10_15)],
  products: [
    // Products define the executables and libraries a package produces, making them visible to other packages.
    .library(
      name: "HelloKit",
      targets: ["HelloKit"]
    ),
    .plugin(name: "ProtoTypeGeneratorPlugin", targets: ["ProtoTypeGeneratorPlugin"])
  ],
  dependencies: [
    .package(url: "https://github.com/apple/swift-syntax.git", from: "509.0.0"),
    .package(url: "https://github.com/apple/swift-argument-parser.git", from: "1.2.0")
    
    //    .package(name: "CLib", path: "../shared/clib")
  ],
  targets: [
    // Targets are the basic building blocks of a package, defining a module or a test suite.
    // Targets can depend on other targets in this package and products from dependencies.
    .plugin(
      name: "ProtoTypeGeneratorPlugin",
      capability: .buildTool(),
      dependencies: [
        "GeneratorTool"
      ]
    ),
    .executableTarget(
      name: "GeneratorTool",
      dependencies: [
        .product(name: "SwiftSyntax", package: "swift-syntax"),
        .product(name: "SwiftSyntaxBuilder", package: "swift-syntax"),
        .product(name: "ArgumentParser", package: "swift-argument-parser"),
      ],
      resources: [
        .copy("../../../shared/json/hello_world.json"),
        .copy("../../../shared/proto/hello.proto")
      ]
    ),
    .executableTarget(
      name: "HelloKitExecutable",
      dependencies: [
        "HelloKit"
      ]
      //      dependencies: ["HelloKit", "CLib"]
    ),
    .target(
      name: "HelloKit",
      resources: [
        .copy("../../../shared/json/hello_world.json"),
        .copy("../../../shared/proto/hello.proto")
      ]
    ),
    .testTarget(
      name: "HelloKitTests",
      dependencies: ["HelloKit"]
    ),
  ]
)
