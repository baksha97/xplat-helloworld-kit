/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

class Library {
    fun someLibraryMethod(): Boolean {
        return true
    }


}

class MyApp {
    fun readJsonResource(): String {
        // Make sure the resource path starts with a slash ('/')
        val stream = this::class.java.getResourceAsStream("/hello_world.json")
            ?: throw IllegalArgumentException("Resource not found!")
        return stream.bufferedReader(Charsets.UTF_8).use { it.readText() }
    }
}

fun main() {
    val app = MyApp()
    val jsonContent = app.readJsonResource()
    println("JSON Content:\n$jsonContent")
}
