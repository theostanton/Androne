package com.theostanton.androne.util

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3

fun <A, B, C> combineLatest(
    observableA: Observable<A>,
    observableB: Observable<B>,
    observableC: Observable<C>
): Observable<Triple<A, B, C>> {
  return Observable.combineLatest(
      observableA, observableB, observableC,
      Function3 { a, b, c -> Triple(a, b, c) }
  )
}

fun <T, U> Observable<T>.combineLatestWith(observable: Observable<U>): Observable<Pair<T, U>> {
  return Observable.combineLatest(this, observable, BiFunction<T, U, Pair<T, U>> { t, u -> Pair(t, u) })
}

fun <T, U> Observable<T>.withLatestFrom(observable: Observable<U>): Observable<Pair<T, U>> {
  return withLatestFrom(observable, BiFunction<T, U, Pair<T, U>> { t, u -> Pair(t, u) })
}