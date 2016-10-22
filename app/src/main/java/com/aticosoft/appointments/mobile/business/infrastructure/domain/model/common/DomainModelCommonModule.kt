package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedInterval
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.ValueObjects
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.DomainModelCommonEventModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

/**
 * Created by Rodrigo Quesada on 07/10/16.
 */
@Module(includes = arrayOf(DomainModelCommonBaseModule::class, DomainModelCommonEventModule::class))
internal class DomainModelCommonModule

@Module
/*internal*/ class DomainModelCommonBaseModule {

    @Provides @ElementsIntoSet @ValueObjects
    fun provideValueObjects(): Set<Class<*>> = setOf(EmbeddedInterval::class.java)
}