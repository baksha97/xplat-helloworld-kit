# xplat-helloworld-kit

This respository serves as a template hierarchy for how a cross platform library can be created for both Swift and 
Kotlin when sharing select resources.

This architecture is ideal in a monorepo scenario. 

The reason behind the structure includes:
- Allows shared schema to be centralized in one location
- Allows cross-platform time checks for the data in `/shared`
- Delivers framework for these tools to synchronize schemas such as protobuf, openapi, etc.

In an ideal world, we'd be able to have `Package.swift` and `settings.gradle.kts` at the root of the project
so there would be no ambiguity when doing development. This can more easily be done when using tools like `bazel` which 
centralize package/executable definitions.
