package com.theostanton.androne

import android.app.Application
import android.content.Context
import com.theostanton.androne.di.AppComponent
import com.theostanton.androne.di.AppModule
import com.theostanton.androne.di.DaggerAppComponent
import com.theostanton.common.Logger
import timber.log.Timber

class BaseApplication : Application(), Logger {

  val component: AppComponent by lazy {
    DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
  }

}

val Context.component
  get() = (applicationContext as BaseApplication).component