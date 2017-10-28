package com.theostanton.androne.di.sensors

import android.hardware.SensorManager
import com.theostanton.androne.GyroReading
import com.theostanton.androne.sensors.GyroSource
import com.theostanton.l3gd20.L3GD20
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

@Module
class GyroModule {

  @Singleton
  @Provides
  fun providesGyroSource(i2cBus: String, sensorManager: SensorManager): GyroSource {
    return GyroSource(i2cBus, sensorManager)
  }

  @Singleton
  @Provides
  fun providesGyroObservable(gyroSource: GyroSource): Observable<GyroReading> {
    return gyroSource.observable
  }

}