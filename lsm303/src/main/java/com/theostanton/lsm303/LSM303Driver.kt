package com.theostanton.lsm303

import android.hardware.Sensor
import android.hardware.SensorManager
import com.google.android.things.userdriver.UserDriverManager
import com.google.android.things.userdriver.UserSensor
import com.google.android.things.userdriver.UserSensorDriver
import com.google.android.things.userdriver.UserSensorReading
import com.theostanton.common.debug
import java.lang.IllegalStateException

class LSM303Driver(bus: String, val magEnabled: Boolean = true, val accEnabled: Boolean = true) : AutoCloseable {

  var acc: LSM303Acc? = if (accEnabled) LSM303Acc(bus) else null
  var mag: LSM303Mag? = if (magEnabled) LSM303Mag(bus) else null
  private var userSensor: UserSensor? = null

  fun registerAcc() {
    if (!accEnabled) {
      return
    }
    debug("registerAcc")
    acc ?: throw IllegalStateException("Cannot register closed driver")
    acc?.apply {
      if (userSensor == null) {
        userSensor = build(this)
        UserDriverManager.getManager().registerSensor(userSensor)
      }
    }

  }

  fun registerMag() {
    if (!magEnabled) {
      return
    }
    debug("registerMag")
    mag ?: throw IllegalStateException("Cannot register closed driver")
    mag?.apply {
      if (userSensor == null) {
        userSensor = build(this)
        UserDriverManager.getManager().registerSensor(userSensor)
      }
    }
    debug("registered mag=$mag userSensor=$userSensor")
  }

  fun unregister() {
    if (userSensor != null) {
      UserDriverManager.getManager().unregisterSensor(userSensor)
      userSensor = null
    }
  }

  override fun close() {
    mag?.close()
    mag = null
    acc?.close()
    acc = null
  }


  companion object {

    fun build(lsm303: LSM303Acc): UserSensor {
      return UserSensor.Builder()
          .setType(Sensor.TYPE_ACCELEROMETER)
          .setName(LSM303Acc.DRIVER_NAME)
          .setMinDelay(1000)
          .setMaxDelay(2000)
          .setDriver(object : UserSensorDriver() {
            override fun read(): UserSensorReading {
              val sample = lsm303.readSample()
              return UserSensorReading(sample, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
            }
          })
          .build()
    }

    fun build(lsm303: LSM303Mag): UserSensor {
      return UserSensor.Builder()
          .setType(Sensor.TYPE_MAGNETIC_FIELD)
          .setName(LSM303Mag.DRIVER_NAME)
          .setMinDelay(1000)
          .setMaxDelay(2000)
          .setDriver(object : UserSensorDriver() {
            override fun read(): UserSensorReading {
              val sample = lsm303.readSample()
              return UserSensorReading(sample, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
            }
          })
          .build()
    }
  }
}