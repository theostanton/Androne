package com.theostanton.common

fun concat(vararg strs: String, paddingLength: Int = 5): String {
  return strs.toList().concat(paddingLength)
}

fun List<String>.concat(paddingLength: Int = 5): String {
  return fold("", { acc, s -> acc + s.padStart(paddingLength) })
}

fun String.pad(length: Int = 7) = padEnd(length)
fun Float.pad(length: Int = 7) = toString().padEnd(length)