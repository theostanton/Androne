package com.theostanton.androne

import com.theostanton.androne.util.combineLatest
import com.theostanton.common.Logger
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class OrientationSource(
    magObservable: Observable<MagReading>,
    accObservable: Observable<AccReading>,
    gyroObservable: Observable<GyroReading>
) : Logger {

  companion object {
    // Ratio of gyro vs acc angle readings
    const val RATIO = 0.2f
    const val INV_RATIO = 1f - RATIO
  }

  private val readingsObservable =
      combineLatest(magObservable, accObservable, gyroObservable)
          .throttleFirst(100, TimeUnit.MILLISECONDS)
          .share()

  init {
    readingsObservable
        .subscribe { (mag, acc, gyro) ->
          log("$mag")
          log("$acc")
          log("$gyro")
        }
  }

  val observable = readingsObservable
      .map { (mag, acc, gyro) ->
        val u = acc.u * RATIO + gyro.u * INV_RATIO
        val v = acc.v * RATIO + gyro.v * INV_RATIO
        Orientation(
            System.nanoTime(),
            u,
            gyro.uRate,
            v,
            gyro.vRate,
            mag.w,
            gyro.wRate
        )

      }
      .share()
}

/**
 * 	a.angle[ROLL]   = atan2( (float)compass.a.x * ACCGAIN , sqrt( ACCGAINSQ * ( sq( (float)compass.a.y ) + sq( (float)compass.a.z ) ) ) ) ;
 */

/**
 * u - roll
 * v - pitch
 * w - bearing
 */

data class Orientation(
    val time: Long,
    val u: Float,
    val uRate: Float,
    val v: Float,
    val vRate: Float,
    val w: Float,
    val wRate: Float
)