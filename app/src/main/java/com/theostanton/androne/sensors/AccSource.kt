package com.theostanton.androne.sensors

import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.theostanton.androne.AccReading
import com.theostanton.androne.Reading

class AccSource(i2cBus: String, sensorManager: SensorManager) : SensorSource<AccReading>(i2cBus, sensorManager) {

  override fun SensorEvent.toReading(): AccReading {
    val x = values[0]
    val y = values[1]
    val z = values[2]
    return AccReading(x, y, z)
  }

  override fun load() {

  }
}