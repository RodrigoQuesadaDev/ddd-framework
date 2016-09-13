@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.rx.testing

import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import rx.schedulers.TestScheduler

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
inline fun <T> Observable<T>.testSubscribe() = TestSubscriber<T>().let { subscriber ->

    subscribe(subscriber)

    triggerTestSchedulerActions()

    subscriber.apply { assertNoErrors() }
}

inline fun <T> TestSubscriber<T>.firstEvent(): T = onNextEvents.first()

inline fun triggerTestSchedulerActions() {
    with(Schedulers.computation()) { if (this is TestScheduler) triggerActions() }
}