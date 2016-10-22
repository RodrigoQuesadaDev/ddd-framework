package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.common.event.TestDomainModelCommonEventModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.DomainModelCommonBaseModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 19/10/16.
 */
@Module(includes = arrayOf(DomainModelCommonBaseModule::class, TestDomainModelCommonEventModule::class))
internal class TestDomainModelCommonModule