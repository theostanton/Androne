package com.theostanton.androne

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.google.android.things.pio.PeripheralManagerService
import com.jakewharton.rxrelay2.BehaviorRelay
import com.theostanton.common.debug
import com.theostanton.common.pad
import com.theostanton.l3gd20.L3GD20Driver
import com.theostanton.lsm303.LSM303Driver
import timber.log.Timber
import java.io.IOException

object Sensors : SensorEventListener {

  private var lsm303driver: LSM303Driver? = null
  private var l3gd20driver: L3GD20Driver? = null

  val gyro = BehaviorRelay.create<GyroReading>()
  val acc = BehaviorRelay.create<AccReading>()
  val mag = BehaviorRelay.create<MagReading>()

  fun init(context: Context) {
    val sensorManager = context.getSystemService(android.content.Context.SENSOR_SERVICE) as SensorManager

    gyro.accept(GyroReading.empty())
    acc.accept(AccReading.empty())
    mag.accept(MagReading.empty())

    val manager = PeripheralManagerService()
    val deviceList = manager.i2cBusList
    val bus = deviceList.first()

//    scan(false)
//    loadLsm303(sensorManager, bus)
//    loadL3gd20(sensorManager, bus)
//    Orientation.init(context)
  }


  fun loadL3gd20(sensorManager: SensorManager, bus: String) {

    sensorManager.registerDynamicSensorCallback(object : SensorManager.DynamicSensorCallback() {
      override fun onDynamicSensorConnected(sensor: Sensor) {
        if (sensor.type == Sensor.TYPE_GYROSCOPE) {
          Timber.d("Gyroscope sensor connected")
          sensorManager.registerListener(this@Sensors, sensor,
              SensorManager.SENSOR_DELAY_NORMAL)
          Timber.d("listening")
        }
      }
    })
    try {
      l3gd20driver = L3GD20Driver(bus)
      l3gd20driver?.register()
      Timber.d("Gyroscope l3gd20driver registered")
    } catch (e: IOException) {
      Timber.e(e, "Error initializing accelerometer l3gd20driver: ${e.message}")
    }
  }

  fun loadLsm303(sensorManager: SensorManager, bus: String) {

    sensorManager.registerDynamicSensorCallback(object : SensorManager.DynamicSensorCallback() {
      override fun onDynamicSensorConnected(sensor: Sensor) {
        if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
          Timber.d("Accelerometer sensor connected")
          sensorManager.registerListener(this@Sensors, sensor,
              SensorManager.SENSOR_DELAY_NORMAL)
          Timber.d("listening")
        }
        if (sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
          Timber.d("Magnetometer sensor connected")
          sensorManager.registerListener(this@Sensors, sensor,
              SensorManager.SENSOR_DELAY_NORMAL)
          Timber.d("listening")
        }
      }
    })
    try {
      lsm303driver = LSM303Driver(bus, false, true)
      lsm303driver!!.registerAcc()
      lsm303driver!!.registerMag()
    } catch (e: IOException) {
      Timber.e(e, "Error initializing accelerometer lsm303driver: ${e.message}")
    }
  }

  override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    debug("onAccuracyChanged sensor=$sensor accuracy=$accuracy")
  }

  override fun onSensorChanged(sensorEvent: SensorEvent) {

    val x = sensorEvent.values[0]
    val y = sensorEvent.values[1]
    val z = sensorEvent.values[2]

    when (sensorEvent.sensor.type) {
      Sensor.TYPE_GYROSCOPE -> gyro.accept(GyroReading(x, y, z))
      Sensor.TYPE_ACCELEROMETER -> acc.accept(AccReading(x, y, z))
      Sensor.TYPE_MAGNETIC_FIELD -> mag.accept(MagReading(x, y, z))
      else -> throw IllegalArgumentException("Cant handle sensor=${sensorEvent.sensor}")
    }
  }

  fun close() {
    lsm303driver?.unregister()
    lsm303driver?.close()

    l3gd20driver?.unregister()
    l3gd20driver?.close()
  }
}


sealed class Reading(val x: Float, val y: Float, val z: Float, val tag: String) {

  override fun toString(): String {
    return "Reading{${tag.pad()} x=${x.pad()} y=${y.pad()} z=${z.pad()}}"
  }
}

class MagReading(x: Float, y: Float, z: Float) : Reading(x, y, z, "Mag") {
  companion object {
    fun empty() = MagReading(0f, 0f, 0f)
  }
}

class AccReading(x: Float, y: Float, z: Float) : Reading(x, y, z, "Acc") {
  companion object {
    fun empty() = AccReading(0f, 0f, 0f)
  }
}

class GyroReading(x: Float, y: Float, z: Float) : Reading(x, y, z, "Gyro") {
  companion object {
    fun empty() = GyroReading(0f, 0f, 0f)
  }
}