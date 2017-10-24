package com.theostanton.androne.sensors

import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.theostanton.androne.MagReading

class MagSource(i2cBus: String, sensorManager: SensorManager) : SensorSource<MagReading>(i2cBus, sensorManager) {

  override fun SensorEvent.toReading(): MagReading {
    val x = values[0]
    val y = values[1]
    val z = values[2]
    return MagReading(x, y, z)
  }

  override fun load() {

  }
}