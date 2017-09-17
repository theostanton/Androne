package com.theostanton.lsm303

// register addresses

val CTRL_REG1_A       = 0x20
val CTRL_REG2_A       = 0x21
val CTRL_REG3_A       = 0x22
val CTRL_REG4_A       = 0x23
val CTRL_REG5_A       = 0x24
val CTRL_REG6_A       = 0x25 // DLHC only
val HP_FILTER_RESET_A = 0x25 // DLH, DLM only
val REFERENCE_A       = 0x26
val STATUS_REG_A      = 0x27

val OUT_X_L_A         = 0x28
val OUT_X_H_A         = 0x29
val OUT_Y_L_A         = 0x2A
val OUT_Y_H_A         = 0x2B
val OUT_Z_L_A         = 0x2C
val OUT_Z_H_A         = 0x2D

val FIFO_CTRL_REG_A   = 0x2E // DLHC only
val FIFO_SRC_REG_A    = 0x2F // DLHC only

val INT1_CFG_A        = 0x30
val INT1_SRC_A        = 0x31
val INT1_THS_A        = 0x32
val INT1_DURATION_A   = 0x33
val INT2_CFG_A        = 0x34
val INT2_SRC_A        = 0x35
val INT2_THS_A        = 0x36
val INT2_DURATION_A   = 0x37

val CLICK_CFG_A       = 0x38 // DLHC only
val CLICK_SRC_A       = 0x39 // DLHC only
val CLICK_THS_A       = 0x3A // DLHC only
val TIME_LIMIT_A      = 0x3B // DLHC only
val TIME_LATENCY_A    = 0x3C // DLHC only
val TIME_WINDOW_A     = 0x3D // DLHC only

val CRA_REG_M         = 0x00
val CRB_REG_M         = 0x01
val MR_REG_M          = 0x02

val OUT_X_H_M         = 0x03
val OUT_X_L_M         = 0x04
val OUT_Y_H_M         = -1   // The addresses of the Y and Z magnetometer output registers 
val OUT_Y_L_M         = -2   // are reversed on the DLM and DLHC relative to the DLH.
val OUT_Z_H_M         = -3   // These four defines have dummy values so the library can 
val OUT_Z_L_M         = -4   // determine the correct address based on the device type.

val SR_REG_M          = 0x09
val IRA_REG_M         = 0x0A
val IRB_REG_M         = 0x0B
val IRC_REG_M         = 0x0C

val WHO_AM_I_M        = 0x0F // DLM only

val TEMP_OUT_H_M      = 0x31 // DLHC only
val TEMP_OUT_L_M      = 0x32 // DLHC only

val LSM303DLH_OUT_Y_H_M      = 0x05
val LSM303DLH_OUT_Y_L_M      = 0x06
val LSM303DLH_OUT_Z_H_M      = 0x07
val LSM303DLH_OUT_Z_L_M      = 0x08

val LSM303DLM_OUT_Z_H_M      = 0x05
val LSM303DLM_OUT_Z_L_M      = 0x06
val LSM303DLM_OUT_Y_H_M      = 0x07
val LSM303DLM_OUT_Y_L_M      = 0x08

val LSM303DLHC_OUT_Z_H_M     = 0x05
val LSM303DLHC_OUT_Z_L_M     = 0x06
val LSM303DLHC_OUT_Y_H_M     = 0x07
val LSM303DLHC_OUT_Y_L_M     = 0x08