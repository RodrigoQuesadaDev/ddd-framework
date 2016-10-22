package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionsManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventActionsManagerImpl
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.DomainModelCommonEventBaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 19/10/16.
 */
@Module(includes = arrayOf(DomainModelCommonEventBaseModule::class))
internal class TestDomainModelCommonEventModule {

    @Provides @Singleton
    fun provideEventActionsManager(eventActionsManager: TestEventActionsManagerImpl): EventActionsManager = eventActionsManager
}