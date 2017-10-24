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

  private val readingsObservable =
      combineLatest(magObservable, accObservable, gyroObservable)
          .throttleFirst(100, TimeUnit.MILLISECONDS)
          .doOnNext { (mag, acc, gyro) ->
            log("$mag")
            log("$acc")
            log("$gyro")
          }
          .share()

  init {
    readingsObservable.subscribe()
  }

  fun observe(): Observable<Orientation> {
    return readingsObservable.map { Orientation(1f, 2f, 3f) }
  }


}

data class Orientation(val x: Float, val y: Float, val z: Float)