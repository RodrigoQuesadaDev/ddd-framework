package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.DomainModelCommonEventBaseModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 19/10/16.
 */
@Module(includes = arrayOf(DomainModelCommonEventBaseModule::class))
internal class TestDomainModelCommonEventModule