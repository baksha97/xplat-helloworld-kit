// swift-tools-version: 6.0
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
  name: "CLib",
  products: [
    // Products define the executables and libraries a package produces, making them visible to other packages.
    .library(
      name: "CLib",
      targets: ["CLib"]),
  ],
  dependencies: [],
  targets: [
    .target(
      name: "CLib",
      path: ".",
      publicHeadersPath: ".",
      cSettings: [
        .headerSearchPath(".")
      ]
    )
  ]
)
