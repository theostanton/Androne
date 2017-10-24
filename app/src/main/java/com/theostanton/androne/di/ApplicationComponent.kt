package com.theostanton.androne.di

import android.app.Application
import com.theostanton.androne.BaseApplication
import com.theostanton.androne.di.sensors.SensorModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(SensorModule::class))
interface AppComponent : AndroidInjector<BaseApplication> {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun bindApplication(application: BaseApplication): Builder

    fun build(): AppComponent
  }

}