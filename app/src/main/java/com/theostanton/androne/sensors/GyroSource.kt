package com.theostanton.androne.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.theostanton.androne.GyroReading
import com.theostanton.l3gd20.L3GD20Driver
import timber.log.Timber
import java.io.IOException

class GyroSource(i2cBus: String, sensorManager: SensorManager) : SensorSource<GyroReading>(i2cBus, sensorManager) {

  companion object {
    // degress / nanosecond
    // TODO gain is incorrect
    const val GAIN = 1.4735f
  }

  init {
    postInit()
  }

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
  private var lastReading: GyroReading? = null

  override fun SensorEvent.toReading(): GyroReading {
    val now = System.nanoTime()
    val diffMs =
        if (lastReading == null) 0f
        else (now - lastReading!!.timeNano) / 1000_000f

    val uRaw = values[0]
    val vRaw = values[1]
    val wRaw = values[2]

    //TODO Maybe use last merged angle instead
    val lastU = lastReading?.u ?: 0f
    val lastV = lastReading?.v ?: 0f
    val lastW = lastReading?.w ?: 0f

    val uRate = uRaw * GAIN
    val uAngle = lastU + uRate * diffMs
    val vRate = vRaw * GAIN
    val vAngle = lastV + vRate * diffMs
    val wRate = wRaw * GAIN
    val wAngle = lastW + wRate * diffMs

    lastReading = GyroReading(
        uAngle,
        uRate,
        vAngle,
        vRate,
        wAngle,
        wRate,
        now
    )

    return lastReading!!
  }

  /**
   * g.rate[PITCH]  = g.raw[PITCH] / GYROGAIN;
  g.angle[PITCH] = c.angle[PITCH] + g.rate[PITCH] * DTsec; // DT in usec    add [ rate * seconds ] = [ degrees * sec-1 * sec -> degrees ]
   */
}