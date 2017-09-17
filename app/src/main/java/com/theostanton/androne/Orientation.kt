package com.theostanton.androne

import android.content.Context
import com.theostanton.common.Logger
import io.reactivex.Observable
import io.reactivex.functions.Function3
import java.util.concurrent.TimeUnit

object Orientation : Logger {

  fun init(context: Context) {
    Observable.combineLatest(
        Sensors.gyro,
        Sensors.acc,
        Sensors.mag,
        Function3 { gyro: GyroReading, acc: AccReading, mag: MagReading -> Triple(gyro, acc, mag) }
    )
        .throttleFirst(100, TimeUnit.MILLISECONDS)
        .subscribe {
          log("${it.first}")
          log("${it.second}")
          log("${it.third}")
        }
  }

}