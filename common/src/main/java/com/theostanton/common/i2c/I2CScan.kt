package com.theostanton.common.i2c

import com.google.android.things.pio.PeripheralManagerService
import com.theostanton.common.debugTag
import com.theostanton.common.logError
import java.io.IOException


private val TEST_REGISTER = 0x00

fun scan(showFails: Boolean = true) {
  val service = PeripheralManagerService()
  for (address in 0..127) {
    try {
      val device = service.openI2cDevice(BoardDefaults.getI2cBus(), address)
      try {
        device.readRegByte(TEST_REGISTER)
        debugTag("Scan", "Trying: 0x$address - SUCCESS")
      } catch (e: IOException) {
        if (showFails) {
          debugTag("Scan", "Trying: 0x%02X - FAIL", address)
        }
      }

    } catch (e: Exception) {
      logError("Scan", "scan error address=$address message=${e.message}")
    }

  }
}