package com.aticosoft.appointments.mobile.business.infrastructure.domain.common

import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rodrigo on 31/10/15.
 */
@Module
/*internal*/ open class DomainCommonModule {

    @Provides @Singleton
    open fun provideTimeService(): TimeService = TimeService()
}