package com.theostanton.lsm303

import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManagerService
import com.theostanton.common.readFloat
import java.lang.IllegalStateException

class LSM303Acc(bus: String) : AutoCloseable {

  companion object {
    val I2C_ADDRESS = 0x30 shr 1
    val DRIVER_NAME = "LSM303Accel"
  }

  private var device: I2cDevice? = null

  init {
    val pioService = PeripheralManagerService()
    val device = pioService.openI2cDevice(bus, I2C_ADDRESS)
    connect(device)
  }

  private fun connect(device: I2cDevice) {
    if (this.device != null) {
      throw IllegalStateException("Device already connected")
    }
    this.device = device

    device.writeRegByte(CTRL_REG1_A, 0x27)
  }


  override fun close() {
    if (device != null) {
      try {
        device!!.close()
      } finally {
        device = null
      }
    }
  }

  fun readSample(): FloatArray {
    device?.apply {
      val x = readFloat(OUT_X_H_A, OUT_X_L_A,"x")
      val y = readFloat(OUT_Y_H_A, OUT_Y_L_A,"y")
      val z = readFloat(OUT_Z_H_A, OUT_Z_L_A,"z")
      return floatArrayOf(x, y, z)
    }
    error("Device not connected")
  }

}