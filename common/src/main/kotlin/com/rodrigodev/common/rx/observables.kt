@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.rx

import com.rodrigodev.common.rx.Observables.interval
import org.joda.time.Duration
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Rodrigo Quesada on 29/10/15.
 */
object Observables {
    inline fun interval(initialDelay: Duration, period: Duration) = Observable.interval(initialDelay.millis, period.millis, TimeUnit.MILLISECONDS)

    inline fun timer(delay: Duration) = Observable.timer(delay.millis, TimeUnit.MILLISECONDS)
}

/**
 * Returns an Observable that emits only the last item emitted by the source Observable during sequential
 * time windows of a specified duration. This variation of the method uses a different interval duration
 * for the first time window.
 * */
inline fun <T> Observable<T>.throttleLast(initialIntervalDuration: Duration, intervalDuration: Duration): Observable<T> = sample(interval(initialIntervalDuration, intervalDuration))

inline fun <T> Observable<T>.throttleLast(intervalDuration: Duration) = throttleLast(intervalDuration.millis, TimeUnit.MILLISECONDS)

inline fun <T> Observable<T>.delaySubscription(delay: Duration) = delaySubscription(delay.millis, TimeUnit.MILLISECONDS)

inline fun <T> Observable<T>.firstOrNull(crossinline predicate: (T) -> Boolean): Observable<T?> = firstOrDefault<T>(null) { predicate(it) }

inline fun <T> Observable<T>.firstOrNothing(crossinline predicate: (T) -> Boolean): Observable<T?> = firstOrNull { predicate(it) }.filter { it != null }

inline fun <T> Observable<T>.repeatWhenChangeOccurs(changes: Observable<*>): Observable<T> = repeatWhen({ o -> o.zipWith(changes, { v, c -> c }) })