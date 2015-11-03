package com.rodrigodev.common.rx

import org.joda.time.Duration
import rx.schedulers.TestScheduler
import java.util.concurrent.TimeUnit

/**
* Created by Rodrigo Quesada on 29/10/15.
*/
@Suppress("NOTHING_TO_INLINE")
inline fun TestScheduler.advanceTimeBy(delayTime: Duration) {
    advanceTimeBy(delayTime.millis, TimeUnit.MILLISECONDS)
}