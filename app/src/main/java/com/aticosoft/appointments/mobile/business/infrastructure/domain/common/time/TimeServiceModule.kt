package com.aticosoft.appointments.mobile.business.infrastructure.domain.common.time

import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.common.time.TimeServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
/*internal*/ class TimeServiceModule {

    @Provides @Singleton
    fun provideTimeService(service: TimeServiceImpl): TimeService = service
}