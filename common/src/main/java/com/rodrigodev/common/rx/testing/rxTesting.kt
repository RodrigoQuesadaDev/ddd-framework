package com.rodrigodev.common.testing

import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import rx.schedulers.TestScheduler

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
fun <T> Observable<T>.testSubscribe() = TestSubscriber<T>().let { subscriber ->

    subscribe(subscriber)

    (Schedulers.computation() as? TestScheduler)?.run { triggerActions() }

    subscriber.apply { assertNoErrors() }
}

fun <T> TestSubscriber<T>.firstEvent(): T = onNextEvents.first()