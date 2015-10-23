package com.aticosoft.appointments.mobile.business.domain.common.time

import org.joda.time.Duration
import java.util.*
import javax.inject.Singleton

/**
 * Created by rodrigo on 30/10/15.
 */
@Singleton
/*internal*/ open class TimeService {

    private val randomGenerator = Random()

    fun randomDuration(maxDuration: Duration): Duration {
        return Duration.millis((randomScalar() * maxDuration.millis).toLong())
    }

    protected open fun randomScalar(): Double = randomGenerator.nextDouble()
}