package com.aticosoft.appointments.mobile.business.domain.testing.common.time

import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 31/10/15.
 */
@Singleton
internal class TestTimeService @Inject constructor() : TimeService() {

    var randomScalar: Double = 1.0

    override fun randomScalar() = randomScalar
}