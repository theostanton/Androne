package com.theostanton.lsm303

import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManagerService
import com.theostanton.common.debug
import com.theostanton.common.debugTag
import com.theostanton.common.logError
import java.lang.IllegalStateException

class LSM303Mag(bus: String) : AutoCloseable {

  companion object {
    val I2C_ADDRESS = 0x24 shr 1
    val DRIVER_NAME = "LSM303Mag"
  }

  var device: I2cDevice? = null

  init {
    val pioService = PeripheralManagerService()
    try {
      val device = pioService.openI2cDevice(bus, I2C_ADDRESS)
      connect(device)
    } catch (e: Exception) {
      logError("LSM303 init error ${e.message}")
    }
  }

  private fun connect(device: I2cDevice) {
    if (this.device != null) {
      throw IllegalStateException("Device already connected")
    }
    this.device = device

    try {
      device.writeRegByte(0x01, 0x00)
    } catch (e: Exception) {
      logError("LSM303Mag init erro ${e.message}")
    }
  }

  override fun close() {
    device?.close()
    device = null
  }


  fun readSample(): FloatArray {
    device?.apply {
      debug("getting sample")

      try {
        val x = readRegByte(0x03)
        debugTag("LSM303Mag", "x=$x")
        return floatArrayOf(x.toFloat())
      } catch (e: Exception) {
        logError("readSample error ${e.message}")
        return floatArrayOf()
      }
//      val x = readFloat(OUT_X_H_M, OUT_X_L_M, "x")
//      val y = readFloat(OUT_Y_H_M, OUT_Y_L_M, "y")
//      val z = readFloat(OUT_Z_H_M, OUT_Z_L_M, "z")
//      debugTag("result", "x=$x y=$y z=$z")

    }
    error("Device not connected")
  }
}