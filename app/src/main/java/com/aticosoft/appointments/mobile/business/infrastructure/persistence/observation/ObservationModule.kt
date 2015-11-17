package com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation

import dagger.Module

/**
 * Created by Rodrigo Quesada on 18/11/15.
 */
@Module(includes = arrayOf(EntityListenersModule::class, QueryViewsModule::class))
internal class ObservationModule