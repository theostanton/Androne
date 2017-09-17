package com.theostanton.common

import com.google.android.things.pio.I2cDevice


fun I2cDevice.readFloat(highAddress: Int, lowAddress: Int, tag: String? = null): Float {
  val highByte = readRegWord(highAddress)
  val lowByte = readRegWord(lowAddress)

//  val value = ((highByte.toInt() shl 8) or lowByte.toInt()) shr 4

  return highByte.toFloat() / 1500f
}