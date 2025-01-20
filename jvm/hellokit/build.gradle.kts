/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.11.1/userguide/building_java_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.protobuf)
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    gradlePluginPortal()
}

val json = file("../../shared/json/hello_world.json")
println(json.absolutePath)
val proto = file("../../shared/proto/hello.proto")
println(proto.absolutePath)

// Instead of adding the file as a resource directory, copy it into the resources folder during build
tasks.processResources {
    from(json) {
        // Optionally rename or place it in a subfolder inside resources
        into("")
    }
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
    implementation("com.google.protobuf:protobuf-kotlin:4.29.3")
    implementation("com.google.protobuf:protobuf-java:4.29.3")
    implementation("com.google.protobuf:protobuf-java-util:4.29.3")
    implementation("com.google.code.gson:gson:2.11.0")
}

// Define the task
tasks.compileKotlin {
    dependsOn("generateSealedClass")
}
tasks.register<GenerateSealedClassTask>("generateSealedClass") {
    protoFile = proto
    jsonFile = json
    outputDir.set(layout.projectDirectory.dir("src/main/kotlin"))
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("2.0.20")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

sourceSets {

    main {

        proto {
            srcDir("src/main/protocol")
            srcDir("src/main/protocolbuffers")
            srcDir("src/test/protocol")
            srcDir("src/test/protocolbuffers")
            srcDir("../../shared/proto")
            // In addition to the default '**/*.proto' (use with caution).
            // Using an extension other than 'proto' is NOT recommended,
            // because when proto files are published along with class files, we can
            // only tell the type of file from its extension.
//            include '**/*.protodevel'
        }
    }
}