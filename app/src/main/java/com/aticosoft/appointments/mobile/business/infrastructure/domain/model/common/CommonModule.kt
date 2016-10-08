package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedInterval
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.ValueObjects
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

/**
 * Created by Rodrigo Quesada on 07/10/16.
 */
@Module
internal class CommonModule {

    @Provides @ElementsIntoSet @ValueObjects
    fun provideValueObjects(): Set<Class<*>> = setOf(EmbeddedInterval::class.java)
}