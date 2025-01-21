package org.example.protos

import kotlin.String

public sealed class HelloType(
  public val hello: Hello,
) {
  public data object Default : HelloType(Hello.newBuilder()
              .setName("default")
              .setMessage("Hello, wonderful world!")
              .setMultiplier(42)
              .build())

  public data object Greeting : HelloType(Hello.newBuilder()
              .setName("greeting")
              .setMessage("Greetings from protobuf land")
              .setMultiplier(7)
              .build())

  public data object Random : HelloType(Hello.newBuilder()
              .setName("random")
              .setMessage("Welcome to the future")
              .setMultiplier(365)
              .build())

  public data object Fun : HelloType(Hello.newBuilder()
              .setName("fun")
              .setMessage("Random messages are fun")
              .setMultiplier(123)
              .build())

  public data object Rock : HelloType(Hello.newBuilder()
              .setName("rock")
              .setMessage("Protocol buffers rock")
              .setMultiplier(256)
              .build())

  public companion object {
    public fun fromName(name: String): HelloType = when (name) {
      "default" -> Default
      "greeting" -> Greeting
      "random" -> Random
      "fun" -> Fun
      "rock" -> Rock
      else -> throw IllegalArgumentException("Unknown name: $name")
    }
  }
}
