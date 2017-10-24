package com.theostanton.androne.di

import android.content.Context
import com.theostanton.androne.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

  @Singleton
  @Provides
  internal fun providesContext(application: BaseApplication): Context {
    return application
  }

}