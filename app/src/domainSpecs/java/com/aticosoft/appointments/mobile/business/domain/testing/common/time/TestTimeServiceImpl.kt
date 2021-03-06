package com.aticosoft.appointments.mobile.business.domain.testing.common.time

import com.aticosoft.appointments.mobile.business.domain.common.time.TimeServiceImpl
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Singleton
internal class TestTimeServiceImpl @Inject protected constructor() : TimeServiceImpl() {

    var randomScalar: Double = 1.0

    override fun randomScalar() = randomScalar
}