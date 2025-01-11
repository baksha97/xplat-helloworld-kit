// swift-tools-version: 6.0
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
  name: "HelloKit",
  products: [
    // Products define the executables and libraries a package produces, making them visible to other packages.
    .library(
      name: "HelloKit",
      targets: ["HelloKit"]),
  ],
  dependencies: [
    .package(url: "https://github.com/apple/swift-protobuf.git", from: "1.20.0")
  ],
  targets: [
    // Targets are the basic building blocks of a package, defining a module or a test suite.
    // Targets can depend on other targets in this package and products from dependencies.
    .executableTarget(
      name: "HelloKitExecutable",
      dependencies: ["HelloKit"]
    ),
    .target(
      name: "HelloKit",
      dependencies: [
        .product(name: "SwiftProtobuf", package: "swift-protobuf"),
      ],
      resources: [
        .copy("../../../shared/json/hello_world.json")
      ],
      plugins: [
        .plugin(name: "SwiftProtobufPlugin", package: "swift-protobuf"),
      ]
    ),
    .testTarget(
      name: "HelloKitTests",
      dependencies: ["HelloKit"]
    ),
  ]
)
