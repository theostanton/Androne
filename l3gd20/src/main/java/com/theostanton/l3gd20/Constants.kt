package com.theostanton.lsm303.l3gd20


val WHO_AM_I = 0x0F
val CTRL_REG1 = 0x20
val CTRL_REG2 = 0x21
val CTRL_REG3 = 0x22
val CTRL_REG4 = 0x23
val CTRL_REG5 = 0x24
val REFERENCE = 0x25
val OUT_TEMP = 0x26
val STATUS_REG = 0x27
val OUT_X_L = 0x28
val OUT_X_H = 0x29
val OUT_Y_L = 0x2A
val OUT_Y_H = 0x2B
val OUT_Z_L = 0x2C
val OUT_Z_H = 0x2D
val FIFO_CTRL_REG = 0x2E
val FIFO_SRC_REG = 0x2F
val INT1_CFG = 0x30
val INT1_SRC = 0x31
val TSH_XH = 0x32
val TSH_XL = 0x33
val TSH_YH = 0x34
val TSH_YL = 0x35
val TSH_ZH = 0x36
val TSH_ZL = 0x37
val INT1_DURATION = 0x38

val L3GD20_DPS_TO_RADS = 0.017453293F  // degress/s to rad/s multiplier

enum class Range(val dps: Float, val fullScaleSelection: Int) {
  LOW(0.00875f, 0x00), // Roughly 22/256 for fixed point match
  MEDIUM(0.0175F, 0x10), // Roughly 45/256
  HIGH(0.070F, 0x20); // Roughly 18/256
}