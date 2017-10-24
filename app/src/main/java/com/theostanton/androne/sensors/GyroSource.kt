package com.theostanton.androne.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.theostanton.androne.GyroReading
import com.theostanton.l3gd20.L3GD20Driver
import timber.log.Timber
import java.io.IOException

class GyroSource(i2cBus: String, sensorManager: SensorManager) : SensorSource<GyroReading>(i2cBus, sensorManager) {
  override fun load() {
    sensorManager.registerDynamicSensorCallback(object : SensorManager.DynamicSensorCallback() {
      override fun onDynamicSensorConnected(sensor: Sensor) {
        if (sensor.type == Sensor.TYPE_GYROSCOPE) {
          sensorManager.registerListener(this@GyroSource, sensor,
              SensorManager.SENSOR_DELAY_NORMAL)
        }
      }
    })
    try {
      l3gd20driver = L3GD20Driver(i2cBus)
      l3gd20driver?.register()
    } catch (e: IOException) {
      Timber.e(e, "Error initializing accelerometer l3gd20driver: ${e.message}")
    }
  }


  private var l3gd20driver: L3GD20Driver? = null

  override fun SensorEvent.toReading(): GyroReading {
    val x = values[0]
    val y = values[1]
    val z = values[2]
    return GyroReading(x, y, z)
  }
}