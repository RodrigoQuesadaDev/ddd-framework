package com.aticosoft.appointments.mobile.business.infrastructure.domain

import com.aticosoft.appointments.mobile.business.infrastructure.domain.common.DomainCommonModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule
import dagger.Module

/**
* Created by Rodrigo Quesada on 31/10/15.
*/
@Module(includes = arrayOf(DomainCommonModule::class, DomainModelModule::class))
internal class DomainModule