package com.theostanton.androne.controller

import com.theostanton.androne.OrientationSource
import com.theostanton.androne.util.combineLatestWith

class Controller(
    private val orientationSource: OrientationSource,
    private val receiver: Receiver,
    private val coefficients: Coefficients
) {

  val observable = orientationSource.observable
      .combineLatestWith(receiver.observable)
      .map { (orientation, command) ->
        Errors(
            orientation.u - command.u,
            orientation.v - command.v,
            command.dw
        )
      }
      .map { errors ->

      }

  data class Errors(
      val u: Float,
//      val du: Float,
      val v: Float,
//      val dv: Float,
      val w: Float
//      val dw: Float
  )

  data class Correction(
      val a: Float,
      val b: Float,
      val c: Float,
      val d: Float
  )

}