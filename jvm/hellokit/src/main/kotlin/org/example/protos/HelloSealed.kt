package org.example.protos

import kotlin.String
import kotlin.collections.List

public sealed class HelloSealed(
  public val hello: Hello,
) {
  public data object Default : HelloSealed(Hello.newBuilder()
              .setName("default")
              .setMessage("Hello, wonderful world!")
              .setMultiplier(42)
              .build())

  public data object Greeting : HelloSealed(Hello.newBuilder()
              .setName("greeting")
              .setMessage("Greetings from protobuf land")
              .setMultiplier(7)
              .build())

  public data object Random : HelloSealed(Hello.newBuilder()
              .setName("random")
              .setMessage("Welcome to the future")
              .setMultiplier(365)
              .build())

  public data object Fun : HelloSealed(Hello.newBuilder()
              .setName("fun")
              .setMessage("Random messages are fun")
              .setMultiplier(123)
              .build())

  public data object Rock : HelloSealed(Hello.newBuilder()
              .setName("rock")
              .setMessage("Protocol buffers rock")
              .setMultiplier(256)
              .build())

  public companion object {
    public val all: List<HelloSealed>
      get() = listOf(Default, Greeting, Random, Fun, Rock)

    public fun fromName(name: String): HelloSealed = when (name) {
      "default" -> Default
      "greeting" -> Greeting
      "random" -> Random
      "fun" -> Fun
      "rock" -> Rock
      else -> throw IllegalArgumentException("Unknown name: $name")
    }
  }
}
