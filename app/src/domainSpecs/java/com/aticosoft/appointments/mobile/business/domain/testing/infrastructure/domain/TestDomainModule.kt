package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.TestDomainCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestDomainModelModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module(includes = arrayOf(TestDomainCommonModule::class, TestDomainModelModule::class))
internal class TestDomainModule