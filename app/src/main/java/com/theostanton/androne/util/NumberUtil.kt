package com.theostanton.androne.util

/*
Map 0-1
 */

fun Float.map(
    lowIn: Float = 0f,
    highIn: Float = 1f,
    lowOut: Float,
    highOut: Float,
    clip: Boolean = false
): Float {
  val inProgress = (this - lowIn) / (highIn - lowIn)
  val outProgress = inProgress * (highOut - lowOut)
  val value = outProgress + lowOut
  return if (clip) value.clip(lowOut, highOut)
  else value
}

fun Float.clip(low: Float, high: Float): Float {
  return Math.min(high, Math.max(this, low))
}
