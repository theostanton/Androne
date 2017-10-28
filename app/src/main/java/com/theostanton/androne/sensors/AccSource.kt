package com.theostanton.androne.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.theostanton.androne.AccReading
import com.theostanton.androne.util.squared
import com.theostanton.androne.util.toDegrees
import com.theostanton.l3gd20.L3GD20
import com.theostanton.l3gd20.L3GD20Driver
import com.theostanton.lsm303.LSM303AccDriver
import com.theostanton.lsm303.LSM303Mag
import timber.log.Timber
import java.io.IOException

class AccSource(i2cBus: String, sensorManager: SensorManager)
  : SensorSource<AccReading>(i2cBus, sensorManager) {

  private var lsm303AccDriver: LSM303AccDriver? = null

  init {
    postInit()
  }

  companion object {
    const val GAIN = 1000.0
    const val GAIN_SQ = GAIN * GAIN
  }

  override fun SensorEvent.toReading(): AccReading {
    val uRaw = values[0].toDouble()
    val vRaw = values[1].toDouble()
    val wRaw = values[2].toDouble()

    val u = Math.atan2(
        uRaw * GAIN,
        Math.sqrt(GAIN_SQ * (vRaw.squared() + wRaw.squared()))
    ).toFloat().toDegrees()

    val v = Math.atan2(
        vRaw * GAIN,
        Math.sqrt(GAIN_SQ * (uRaw.squared() + wRaw.squared()))
    ).toFloat().toDegrees()

    val w = Math.atan2(
        wRaw * GAIN,
        Math.sqrt(GAIN_SQ * (uRaw.squared() + vRaw.squared()))
    ).toFloat().toDegrees()

    return AccReading(u, v, w, System.nanoTime())
  }

  /**
   * 	a.angle[ROLL]   = atan2( (float)compass.a.x * ACCGAIN , sqrt( ACCGAINSQ * ( sq( (float)compass.a.y ) + sq( (float)compass.a.z ) ) ) ) ;
   *  a.angle[PITCH]  = atan2( (float)compass.a.y * ACCGAIN , sqrt( ACCGAINSQ * ( sq( (float)compass.a.z ) + sq( (float)compass.a.x ) ) ) ) ;
   **/

  override fun load() {
    sensorManager.registerDynamicSensorCallback(object : SensorManager.DynamicSensorCallback() {
      override fun onDynamicSensorConnected(sensor: Sensor) {
        if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
          sensorManager.registerListener(this@AccSource, sensor,
              SensorManager.SENSOR_DELAY_NORMAL)
        }
      }
    })
    try {
      lsm303AccDriver = LSM303AccDriver(i2cBus)
      lsm303AccDriver!!.register()
    } catch (e: IOException) {
      Timber.e(e, "Error initializing accelerometer lsm303AccDriver: ${e.message}")
    }
  }
}