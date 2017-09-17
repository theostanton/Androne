package com.theostanton.tx

import com.theostanton.common.Logger
import com.theostanton.common.debug
import com.theostanton.common.logError
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class TX : Logger {

  fun observe() = Observable.create<IntArray> {
    debug("observe")
    while (!it.isDisposed) {
      try {
        val values = getReadings()
        debug("values=$values")
      it.onNext(values)
      } catch (e:Exception){
        logError("TX",e.message!!)
      }
    }
  }.subscribeOn(Schedulers.computation())!!

  external fun getReadings(): IntArray

  companion object {
    init {
      System.loadLibrary("native-lib")
    }
  }

}