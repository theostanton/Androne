package com.theostanton.androne.di.sensors

import android.hardware.SensorManager
import com.theostanton.androne.AccReading
import com.theostanton.androne.sensors.AccSource
import com.theostanton.l3gd20.L3GD20
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Singleton

@Module
class AccModule {

  @Singleton
  @Provides
  fun providesAccSource(i2cBus: String, sensorManager: SensorManager): AccSource {
    Timber.d("init AccSource")
    return AccSource(i2cBus, sensorManager)
  }

  @Singleton
  @Provides
  fun providesAccObservable(accSource: AccSource): Observable<AccReading> {
    return accSource.observable
  }

}