package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.time.TestTimeServiceModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Module(includes = arrayOf(TestTimeServiceModule::class))
internal class TestDomainCommonModule