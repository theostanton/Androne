package com.theostanton.l3gd20

import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManagerService
import com.theostanton.common.readFloat
import com.theostanton.lsm303.l3gd20.*
import java.lang.IllegalStateException

class L3GD20(bus: String) : AutoCloseable {

  companion object {
    val I2C_ADDRESS = 0x6B
    val DRIVER_NAME = "L3GD20"
  }

  private var range: Range = Range.MEDIUM

  var device: I2cDevice? = null

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

    /* Set CTRL_REG1 (0x20)
 ====================================================================
 BIT  Symbol    Description                                   Default
 ---  ------    --------------------------------------------- -------
 7-6  DR1/0     Output data rate                                   00
 5-4  BW1/0     Bandwidth selection                                00
   3  PD        0 = Power-down mode, 1 = normal/sleep mode          0
   2  ZEN       Z-axis enable (0 = disabled, 1 = enabled)           1
   1  YEN       Y-axis enable (0 = disabled, 1 = enabled)           1
   0  XEN       X-axis enable (0 = disabled, 1 = enabled)           1 */

    /* Switch to normal mode and enable all three channels */
    device.writeRegByte(CTRL_REG1, 0x0F)

    /* ------------------------------------------------------------------ */

    /* Set CTRL_REG2 (0x21)
     ====================================================================
     BIT  Symbol    Description                                   Default
     ---  ------    --------------------------------------------- -------
     5-4  HPM1/0    High-pass filter mode selection                    00
     3-0  HPCF3..0  High-pass filter cutoff frequency selection      0000 */

    /* Nothing to do ... keep default values */
    /* ------------------------------------------------------------------ */

    /* Set CTRL_REG3 (0x22)
     ====================================================================
     BIT  Symbol    Description                                   Default
     ---  ------    --------------------------------------------- -------
       7  I1_Int1   Interrupt enable on INT1 (0=disable,1=enable)       0
       6  I1_Boot   Boot status on INT1 (0=disable,1=enable)            0
       5  H-Lactive Interrupt active config on INT1 (0=high,1=low)      0
       4  PP_OD     Push-Pull/Open-Drain (0=PP, 1=OD)                   0
       3  I2_DRDY   Data ready on DRDY/INT2 (0=disable,1=enable)        0
       2  I2_WTM    FIFO wtrmrk int on DRDY/INT2 (0=dsbl,1=enbl)        0
       1  I2_ORun   FIFO overrun int on DRDY/INT2 (0=dsbl,1=enbl)       0
       0  I2_Empty  FIFI empty int on DRDY/INT2 (0=dsbl,1=enbl)         0 */

    /* Nothing to do ... keep default values */
    /* ------------------------------------------------------------------ */

    /* Set CTRL_REG4 (0x23)
     ====================================================================
     BIT  Symbol    Description                                   Default
     ---  ------    --------------------------------------------- -------
       7  BDU       Block Data Update (0=continuous, 1=LSB/MSB)         0
       6  BLE       Big/Little-Endian (0=Data LSB, 1=Data MSB)          0
     5-4  FS1/0     Full scale selection                               00
                                    00 = 250 dps
                                    01 = 500 dps
                                    10 = 2000 dps
                                    11 = 2000 dps
       0  SIM       SPI Mode (0=4-wire, 1=3-wire)                       0 */
    updateRange(range)
  }

  fun updateRange(range: Range) {
    this.range = range
    device!!.writeRegByte(CTRL_REG4, range.fullScaleSelection.toByte())
  }

  override fun close() {
    try {
      device?.close()
    } finally {
      device = null
    }
  }

  fun readSample(): FloatArray {
    device?.apply {
      val x = readFloat(OUT_X_H, OUT_X_L, "x") * range.dps
      val y = readFloat(OUT_Y_H, OUT_Y_L, "y") * range.dps
      val z = readFloat(OUT_Z_H, OUT_Z_L, "z") * range.dps
      return floatArrayOf(x, y, z)
    }
    return floatArrayOf(0f, 0f, 0f)
  }


}