package com.theostanton.lsm303

import android.hardware.Sensor
import android.hardware.SensorManager
import com.google.android.things.userdriver.UserDriverManager
import com.google.android.things.userdriver.UserSensor
import com.google.android.things.userdriver.UserSensorDriver
import com.google.android.things.userdriver.UserSensorReading

class LSM303AccDriver(bus: String) : AutoCloseable {

  var device = LSM303Acc(bus)

  private var userSensor: UserSensor? = null

  fun register() {
    if (userSensor == null) {
      userSensor = build(device)
      UserDriverManager.getManager().registerSensor(userSensor)
    }
  }

  fun unregister() {
    if (userSensor != null) {
      UserDriverManager.getManager().unregisterSensor(userSensor)
      userSensor = null
    }
  }

  override fun close() {
    device.close()
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
  }
}