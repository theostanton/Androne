package com.theostanton.common

import android.util.Log
import kotlin.experimental.and


interface Logger {
  fun log(msg: String) {
    Log.d(javaClass.simpleName, msg)
  }
}

fun Any.debug(msg: String) {
  Log.d(javaClass.simpleName, msg)
}

fun Any.debug(msg: String, vararg anies: Any) {
  Log.d(javaClass.simpleName, String.format(msg, anies))
}

fun debugTag(tag: String, msg: String) {
  Log.d(tag, msg)
}

fun debugTag(tag: String, msg: String, vararg anies: Any) {
  Log.d(tag, String.format(msg, anies))
}

fun Any.logError(msg: String) {
  Log.e(javaClass.simpleName, msg)
}

fun logError(tag: String, msg: String) {
  Log.d(tag, msg)
}

fun List<Any>.printEach() {
  forEachIndexed { i, item ->
    debug("$i - $item")
  }
}

fun Array<Any>.printEach(tag: String? = null) {
  tag?.apply {
    debug("$tag size=$size")
  }
  forEachIndexed { i, item ->
    debug("$i - $item")
  }
}

fun FloatArray.printEach(tag: String? = null) {
  tag?.apply {
    debug("$tag size=$size")

  }
  forEachIndexed { i, item ->
    debug("$i - $item")
  }
}

fun ByteArray.printEach(tag: String? = null) {
  tag?.apply {
    debug("$tag size=$size")
  }
  forEachIndexed { i, item ->
    debug("$i - $item")
  }
}

infix fun Byte.shl(shift: Int): Int = this.toInt() shl shift
infix fun Byte.shr(shift: Int): Int = this.toInt() shr shift
infix fun Short.shl(shift: Int): Int = this.toInt() shl shift
infix fun Short.shr(shift: Int): Int = this.toInt() shr shift

fun Byte.toBits(): BooleanArray {
  val zero = 0.toByte()
  return booleanArrayOf(
      this and 1 != zero,
      this and 2 != zero,
      this and 4 != zero,
      this and 8 != zero,
      this and 16 != zero,
      this and 32 != zero,
      this and 64 != zero,
      this and 0 != zero)
}

fun Short.toBinaryString(): String {
  debug("toBinaryString $this")
  return String.format("%016d", Integer.parseInt(Integer.toBinaryString(toInt())))
}

fun BooleanArray.printEach(tag: String) {
  val str = fold("", { acc, bool -> acc + if (bool) "1" else "0" })
  debug("$tag - $str")
}

fun <T> List<T>.getMod(index: Int) = get(index % size)!!


fun currentThreadName() = Thread.currentThread().name