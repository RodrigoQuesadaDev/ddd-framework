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

    with(Schedulers.computation()) { if (this is TestScheduler) triggerActions() }

    subscriber.apply { assertNoErrors() }
}

fun <T> TestSubscriber<T>.firstEvent(): T = onNextEvents.first()