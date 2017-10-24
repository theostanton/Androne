package com.theostanton.androne

import android.app.Activity
import android.os.Bundle
import com.theostanton.common.*
import com.theostanton.tx.TX
import io.reactivex.disposables.CompositeDisposable


class MainActivity : Activity(), Logger {

  private val disposables = CompositeDisposable()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /*

    debug("onCreate")
    disposables += Sensors.gyro
//        .throttleFirst(1000,TimeUnit.MILLISECONDS)
        .time()
        .buffer(100)
        .map { it.map { it.second }.average() }
        .subscribe {
          //          debugTag("rate=${it.second} gyro=", it.first.toString())
          debug("gyro avgRate=$it")
        }

    val tx = TX()

    disposables += tx.observe()
        .map { it.copyOfRange(1, 10).map { it / 1000 } }
        .buffer(5, 1)
        .map { it.first().mapIndexed { index, _ -> it.map { it[index] }.average() } }
        .map { it.map { ((it - 1000) / 10).toInt().toString() } }
        .subscribe {
          log(it.concat(8))
        }

    disposables += Sensors.acc
//        .throttleFirst(1000,TimeUnit.MILLISECONDS)
        .time()
        .buffer(100)
        .map { it.map { it.second }.average() }
        .subscribe {
          //          debugTag("rate=${it.second} acc=", it.first.toString())
          debug("acc avgRate=$it")
        }

    disposables += Sensors.mag
//        .throttleFirst(1000,TimeUnit.MILLISECONDS)
        .time()
        .buffer(100)
        .map { it.map { it.second }.average() }
        .subscribe {
          //          debugTag("rate=${it.second} mag=", it.first.toString())
          debug("mag avgRate=$it")
        }
        */
  }


  override fun onDestroy() {
    disposables.clear()
    super.onDestroy()
  }

}
