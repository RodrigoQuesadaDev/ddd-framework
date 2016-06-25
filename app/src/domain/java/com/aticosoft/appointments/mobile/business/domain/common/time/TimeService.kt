package com.aticosoft.appointments.mobile.business.domain.common.time

import org.joda.time.Duration
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 30/10/15.
 */
@Singleton
/*internal*/ interface TimeService {

    fun randomDuration(maxDuration: Duration): Duration
}

@Singleton
/*internal*/ open class TimeServiceImpl @Inject constructor() : TimeService {

    private val randomGenerator = Random()

    override fun randomDuration(maxDuration: Duration): Duration {
        return Duration.millis((randomScalar() * maxDuration.millis).toLong())
    }

    protected open fun randomScalar(): Double = randomGenerator.nextDouble()
}