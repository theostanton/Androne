package com.theostanton.androne.di

import com.theostanton.androne.BaseApplication
import com.theostanton.androne.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
  fun inject(app: BaseApplication)
  fun inject(mainActivity: MainActivity)
}