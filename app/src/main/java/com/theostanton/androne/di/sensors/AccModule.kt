package com.theostanton.androne.di.sensors

import android.hardware.SensorManager
import com.theostanton.androne.AccReading
import com.theostanton.androne.GyroReading
import com.theostanton.androne.sensors.AccSource
import com.theostanton.androne.sensors.GyroSource
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

@Module
class AccModule {

  @Singleton
  @Provides
  fun providesAccSource(i2cBus: String, sensorManager: SensorManager): AccSource {
    return AccSource(i2cBus, sensorManager)
  }

  @Singleton
  @Provides
  fun providesAccObservable(accSource: AccSource): Observable<AccReading> {
    return accSource.observe()
  }

}