package com.aticosoft.appointments.mobile.business.infrastructure.domain.application

import com.aticosoft.appointments.mobile.business.infrastructure.domain.application.common.observation.ObservationModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 11/12/15.
 */
@Module(includes = arrayOf(ObservationModule::class))
/*internal*/ class DomainApplicationModule