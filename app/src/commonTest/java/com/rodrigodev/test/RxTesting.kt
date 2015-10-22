package com.rodrigodev.test

import rx.Observable
import rx.observers.TestSubscriber

/**
 * Created by rodrigo on 19/10/15.
 */
fun <T> subscribe(observable: Observable<T>): TestSubscriber<T> {
    val subscriber = TestSubscriber<T>()
    observable.subscribe(subscriber)
    subscriber.assertNoErrors()
    return subscriber
}

fun <T> TestSubscriber<T>.firstEvent(): T {
    return onNextEvents.first()
}