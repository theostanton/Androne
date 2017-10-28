package com.theostanton.androne.di.sensors

import android.hardware.SensorManager
import com.theostanton.androne.GyroReading
import com.theostanton.androne.MagReading
import com.theostanton.androne.sensors.GyroSource
import com.theostanton.androne.sensors.MagSource
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

@Module
class MagModule {

  @Singleton
  @Provides
  fun providesMagSource(i2cBus: String, sensorManager: SensorManager): MagSource {
    return MagSource(i2cBus, sensorManager)
  }

  @Singleton
  @Provides
  fun providesMagObservable(magSource: MagSource): Observable<MagReading> {
    return magSource.observable
  }

}