package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.time

import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.testing.common.time.TestTimeServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
internal class TestTimeServiceModule {

    @Provides @Singleton
    fun provideTimeService(service: TestTimeServiceImpl): TimeService = service
}