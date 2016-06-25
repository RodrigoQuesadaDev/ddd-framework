package com.aticosoft.appointments.mobile.business.infrastructure.domain.common

import com.aticosoft.appointments.mobile.business.infrastructure.domain.common.time.TimeServiceModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Module(includes = arrayOf(TimeServiceModule::class))
/*internal*/ class DomainCommonModule