package com.theostanton.androne.di

import android.content.Context
import com.theostanton.androne.BaseApplication
import com.theostanton.androne.OrientationSource
import com.theostanton.androne.controller.Coefficients
import com.theostanton.androne.controller.Controller
import com.theostanton.androne.controller.Receiver
import com.theostanton.androne.di.sensors.SensorModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(SensorModule::class))
class AppModule(private val application: BaseApplication) {

  @Singleton
  @Provides
  internal fun providesContext(): Context {
    return application
  }

  @Singleton
  @Provides
  internal fun providesController(coefficients: Coefficients, receiver: Receiver, orientationSource: OrientationSource):Controller{
    return Controller(orientationSource,receiver,coefficients)
  }

}