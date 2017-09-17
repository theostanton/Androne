package com.theostanton.androne

import android.app.Application
import com.theostanton.common.Logger
import timber.log.Timber

class BaseApplication : Application(), Logger {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    Sensors.init(this)
  }

}