package com.theostanton.androne

import com.theostanton.common.Logger
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class BaseApplication : DaggerApplication(), Logger {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
    return DaggerAppComponent
  }


  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
  }

}