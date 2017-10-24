package com.theostanton.androne.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.theostanton.androne.Reading
import com.theostanton.common.debug
import io.reactivex.Observable

abstract class SensorSource<R : Reading>(
    protected val i2cBus:String,
    protected val sensorManager: SensorManager
) : SensorEventListener {

  private val relay = BehaviorRelay.create<R>()

  abstract fun SensorEvent.toReading(): R

  fun observe() = relay as Observable<R>

  abstract fun load()

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    debug("onAccuracyChanged sensor=$sensor accuracy=$accuracy")
  }

  override fun onSensorChanged(sensorEvent: SensorEvent) {
    relay.accept(sensorEvent.toReading())
  }
}