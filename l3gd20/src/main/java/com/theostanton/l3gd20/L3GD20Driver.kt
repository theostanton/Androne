package com.theostanton.l3gd20

import android.hardware.Sensor
import android.hardware.SensorManager
import com.google.android.things.userdriver.UserDriverManager
import com.google.android.things.userdriver.UserSensor
import com.google.android.things.userdriver.UserSensorDriver
import com.google.android.things.userdriver.UserSensorReading

class L3GD20Driver(bus: String) : AutoCloseable {

  var device: L3GD20? = L3GD20(bus)
  var userSensor: UserSensor? = null

  fun register() {
    device ?: throw IllegalStateException("Cannot register closed driver")
    device?.apply {
      if (userSensor == null) {
        userSensor = build(this)
        UserDriverManager.getManager().registerSensor(userSensor)
      }
    }
  }

  fun unregister() {
    if (userSensor != null) {
      UserDriverManager.getManager().unregisterSensor(userSensor)
      userSensor = null
    }
  }


  override fun close() {
    device?.close()
    device = null
  }

  companion object {
    fun build(l3GD20: L3GD20):UserSensor{
      return UserSensor.Builder()
          .setType(Sensor.TYPE_GYROSCOPE)
          .setName(L3GD20.DRIVER_NAME)
          .setMinDelay(1000)
          .setMaxDelay(2000)
          .setDriver(object:UserSensorDriver(){
            override fun read(): UserSensorReading {
              val sample = l3GD20.readSample()
              return UserSensorReading(sample, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
            }
          })
          .build()
    }
  }

}