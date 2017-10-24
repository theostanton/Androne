package com.theostanton.androne.di.sensors

import com.theostanton.androne.*
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

@Module(includes = arrayOf(GyroModule::class, AccModule::class, MagModule::class))
class SensorModule {

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
    return orientationSource.observe()
  }

}