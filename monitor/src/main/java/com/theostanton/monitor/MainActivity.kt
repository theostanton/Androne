package com.theostanton.monitor

import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.theostanton.blue.Blue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException


class MainActivity : AppCompatActivity() {

  val disposables = CompositeDisposable()

  val blue = Blue(this, "192.168.178.47:")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    disposables += blue.observe()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          text.text = it
        }
  }

  override fun onDestroy() {
    disposables.clear()
    super.onDestroy()
  }
}


private fun getInetAddress(): InetAddress? {
  try {
    val en = NetworkInterface.getNetworkInterfaces()
    while (en.hasMoreElements()) {
      val networkInterface = en.nextElement() as NetworkInterface

      val enumIpAddr = networkInterface.inetAddresses
      while (enumIpAddr.hasMoreElements()) {
        val inetAddress = enumIpAddr.nextElement() as InetAddress

        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
          return inetAddress
        }
      }
    }
  } catch (e: SocketException) {
    e.printStackTrace()
    Log.e(TAG, "Error getting the network interface information")
  }

  return null
}

private operator fun CompositeDisposable.plusAssign(subscribe: Disposable?) {
  add(subscribe)
}

private operator fun CompositeSubscription.plusAssign(suscribe: Subscription) {
  add(suscribe)
}
