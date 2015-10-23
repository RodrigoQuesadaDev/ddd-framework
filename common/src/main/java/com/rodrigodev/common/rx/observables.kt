package com.rodrigodev.common.rx

import com.rodrigodev.common.rx.Observables.interval
import org.joda.time.Duration
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by rodrigo on 29/10/15.
 */
object Observables {
    @Suppress("NOTHING_TO_INLINE")
    inline fun interval(initialDelay: Duration, period: Duration) = Observable.interval(initialDelay.millis, period.millis, TimeUnit.MILLISECONDS)
}

/**
 * Returns an Observable that emits only the last item emitted by the source Observable during sequential
 * time windows of a specified duration. This variation of the method uses a different interval duration
 * for the first time window.
 * */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<T>.throttleLast(initialIntervalDuration: Duration, intervalDuration: Duration): Observable<T> = sample(interval(initialIntervalDuration, intervalDuration))

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<T>.throttleLast(intervalDuration: Duration) = throttleLast(intervalDuration.millis, TimeUnit.MILLISECONDS)

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<T>.delaySubscription(delay: Duration) = delaySubscription(delay.millis, TimeUnit.MILLISECONDS)
