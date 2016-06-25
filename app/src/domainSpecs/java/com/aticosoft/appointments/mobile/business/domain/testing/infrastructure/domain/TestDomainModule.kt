package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.TestDomainCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestDomainModelModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.application.DomainApplicationModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module(includes = arrayOf(TestDomainCommonModule::class, DomainModelModule::class, TestDomainModelModule::class, DomainApplicationModule::class))
internal class TestDomainModule