package com.theostanton.androne

import com.theostanton.common.pad

sealed class Reading(
    val u: Float,
    val v: Float,
    val w: Float,
    val tag: String,
    val timeNano: Long
) {

  override fun toString(): String {
    return "Reading{${tag.pad()} u=${u.pad()} v=${v.pad()} w=${w.pad()}}"
  }
}

class MagReading(u: Float, v: Float, w: Float, timeNano: Long) : Reading(u, v, w, "Mag", timeNano) {
  companion object {
    fun empty() = MagReading(0f, 0f, 0f,System.nanoTime())
  }
}

class AccReading(u: Float, v: Float, w: Float, timeNano: Long) : Reading(u, v, w, "Acc", timeNano) {
  companion object {
    fun empty() = AccReading(0f, 0f, 0f,System.nanoTime())
  }
}

class GyroReading(
    u: Float,
    val uRate: Float,
    v: Float,
    val vRate: Float,
    w: Float,
    val wRate: Float,
    timeNano: Long
) : Reading(u, v, w, "Gyro", timeNano) {
  companion object {
    fun empty() = GyroReading(0f, 0f, 0f, 0f, 0f, 0f, System.nanoTime())
  }
}