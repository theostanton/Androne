package com.theostanton.androne.controller

import com.theostanton.androne.util.withLatestFrom
import io.reactivex.Observable

/**
 * raw - int of 1000-2000
 * mapped:
 *  throttle    0 - +1
 *  yaw       -45 - +45
 *  roll      -45 - +45
 *  pitch     -45 - +45
 */

class Receiver {

  companion object {
    const val ZERO = 0f
    const val MID = 0.5f
    const val ONE = 1f
    const val ANGLE = 45f
    const val LOW_IN = 1000
    const val HIGH_IN = 2000
    const val THROTTLE_INDEX = 0
    const val YAW_INDEX = 1
    const val PITCH_INDEX = 2
    const val ROLL_INDEX = 3
  }

  val rawObservable = Observable.empty<IntArray>()

  val defaultMappers = arrayOf(
      Mapper(ZERO, ONE), // throttle
      Mapper(ANGLE, -ANGLE), // yaw
      Mapper(ANGLE, -ANGLE), // roll
      Mapper(ANGLE, -ANGLE)  // pitch
  )

  val mappersObservable = Observable.just(defaultMappers)

  val observable = rawObservable
      .withLatestFrom(mappersObservable)
      .map { (raw, mappers) ->
        val mapped = raw.mapIndexed { i, rawValue -> mappers[i].map(rawValue.toFloat()) }
        Desired(
            mapped[THROTTLE_INDEX],
            mapped[YAW_INDEX],
            mapped[PITCH_INDEX],
            mapped[ROLL_INDEX]
        )
      }

  data class Mapper(
      val lowOut: Float,
      val highOut: Float,
      val lowIn: Int = LOW_IN,
      val highIn: Int = HIGH_IN
  ) {
    fun map(raw: Float): Float {
      return 1f
    }
  }
}

data class Desired(
    val throttle: Float,
    val u: Float,
    val v: Float,
    val dw: Float
)

data class Raw(val value: Float)

data class Joystick(val x: Float, val y: Float)