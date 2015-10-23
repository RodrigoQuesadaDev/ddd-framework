package com.rodrigodev.common.testing

import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import rx.schedulers.TestScheduler

/**
 * Created by rodrigo on 19/10/15.
 */
fun <T> subscribe(observable: Observable<T>): TestSubscriber<T> = TestSubscriber<T>().let { subscriber ->

    observable.subscribe(subscriber)

    (Schedulers.computation() as? TestScheduler)?.apply { triggerActions() }

    subscriber.apply { assertNoErrors() }
}

fun <T> TestSubscriber<T>.firstEvent(): T = onNextEvents.first()