package com.theostanton.androne.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.theostanton.androne.Reading
import com.theostanton.common.Logger
import com.theostanton.common.debug
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

abstract class SensorSource<R : Reading>(
    protected val i2cBus: String,
    protected val sensorManager: SensorManager
) : SensorEventListener, Logger {

  private val relay = BehaviorRelay.create<R>()

  open val observable: Observable<R> = (relay as Observable<R>)
      .share()

  private val printInterval: Long? = 500L

  fun postInit(){
    printInterval?.let { interval ->
      observable
          .throttleFirst(interval, TimeUnit.MILLISECONDS)
          .subscribe {
            log("update->$it")
          }
    }
    load()
  }

  abstract fun SensorEvent.toReading(): R

  abstract fun load()

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    debug("onAccuracyChanged sensor=$sensor accuracy=$accuracy")
  }

  override fun onSensorChanged(sensorEvent: SensorEvent) {
    relay.accept(sensorEvent.toReading())
  }
}