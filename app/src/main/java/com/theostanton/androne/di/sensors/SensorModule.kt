package com.theostanton.androne.di.sensors

import android.content.Context
import android.hardware.SensorManager
import com.google.android.things.pio.PeripheralManagerService
import com.theostanton.androne.*
import com.theostanton.l3gd20.L3GD20
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

@Module(includes = arrayOf(GyroModule::class, AccModule::class, MagModule::class))
class SensorModule {

  @Singleton
  @Provides
  fun probidesI2CBus(): String {
    val manager = PeripheralManagerService()
    val deviceList = manager.i2cBusList
    return deviceList.first()
  }

  @Singleton
  @Provides
  fun providesSensorManager(context: Context) = context.getSystemService(android.content.Context.SENSOR_SERVICE) as SensorManager

  @Singleton
  @Provides
  fun providesOrientationSource(
      magObservable: Observable<MagReading>,
      accObservable: Observable<AccReading>,
      gyroObservable: Observable<GyroReading>
  ): OrientationSource {
    return OrientationSource(magObservable, accObservable, gyroObservable)
  }

  @Singleton
  @Provides
  fun providesOrientationObservable(orientationSource: OrientationSource): Observable<Orientation> {
    return orientationSource.observable
  }

}