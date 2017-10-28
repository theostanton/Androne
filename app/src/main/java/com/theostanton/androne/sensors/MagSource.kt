package com.theostanton.androne.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.theostanton.androne.MagReading
import com.theostanton.lsm303.LSM303MagDriver
import io.reactivex.Observable
import timber.log.Timber
import java.io.IOException

class MagSource(i2cBus: String, sensorManager: SensorManager) : SensorSource<MagReading>(i2cBus, sensorManager) {

  private var lsm303MagDriver: LSM303MagDriver? = null

  override val observable = Observable.just(MagReading(0f, 0f, 0f, System.nanoTime()))!!

  init {
    postInit()
  }

  override fun SensorEvent.toReading(): MagReading {
    val uRaw = values[0]
    val vRaw = values[1]
    val wRaw = values[2]
    return MagReading(uRaw, vRaw, wRaw, System.nanoTime())
  }

  override fun load() {
    sensorManager.registerDynamicSensorCallback(object : SensorManager.DynamicSensorCallback() {
      override fun onDynamicSensorConnected(sensor: Sensor) {
        if (sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
          sensorManager.registerListener(this@MagSource, sensor,
              SensorManager.SENSOR_DELAY_NORMAL)
        }
      }
    })
    try {
      lsm303MagDriver = LSM303MagDriver(i2cBus)
      lsm303MagDriver!!.register()
    } catch (e: IOException) {
      Timber.e(e, "Error initializing magnetometer lsm303MagDriver: ${e.message}")
    }

  }
}