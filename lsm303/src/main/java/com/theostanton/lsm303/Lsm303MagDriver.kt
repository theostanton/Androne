package com.theostanton.lsm303

import android.hardware.Sensor
import android.hardware.SensorManager
import com.google.android.things.userdriver.UserDriverManager
import com.google.android.things.userdriver.UserSensor
import com.google.android.things.userdriver.UserSensorDriver
import com.google.android.things.userdriver.UserSensorReading

class LSM303MagDriver(bus: String) : AutoCloseable {

  var device = LSM303Mag(bus)

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