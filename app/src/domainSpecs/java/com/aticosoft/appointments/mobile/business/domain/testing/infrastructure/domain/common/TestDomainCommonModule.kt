package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common

import com.aticosoft.appointments.mobile.business.domain.testing.common.time.TestTimeService
import com.aticosoft.appointments.mobile.business.infrastructure.domain.common.DomainCommonModule
import javax.inject.Inject
import javax.inject.Provider

/**
* Created by Rodrigo Quesada on 31/10/15.
*/
internal class TestDomainCommonModule : DomainCommonModule() {

    @Inject protected lateinit var testTimeServiceProvider: Provider<TestTimeService>

    override fun provideTimeService() = testTimeServiceProvider.get()
}