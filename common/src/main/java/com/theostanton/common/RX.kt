package com.theostanton.common

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(suscribe: Disposable) {
  add(suscribe)
}

fun <T> Observable<T>.time(): Observable<Pair<T, Long>> {
  var last = System.nanoTime()
  return map {
    val now = System.nanoTime()
    val since = now - last
    last = now
    Pair(it, since)
  }
}

