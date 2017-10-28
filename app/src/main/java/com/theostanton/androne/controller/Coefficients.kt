package com.theostanton.androne.controller

import io.reactivex.Observable

/**
 * p - potential
 * i - integral
 * d - differential
 *
 * u - roll
 * v - pitch
 * w - bearing / yaw
 *
 * x - left to right
 * y - forwards and backwards
 * z - up and down
 */

class Coefficients {

  companion object {
    const val P = 0.5f
    const val I = 0.5f
    const val D = 0.5f
  }

  val observable: Observable<PID> = Observable.just(PID(P, I, D))

}

data class PID(val p: Float, val i: Float, val d: Float)
