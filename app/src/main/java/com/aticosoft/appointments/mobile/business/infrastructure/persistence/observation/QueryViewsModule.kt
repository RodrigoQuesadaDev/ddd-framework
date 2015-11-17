package com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
@Module
/*internal*/ open class QueryViewsModule {

    @Provides @Singleton @QueryViews
    open fun provideQueryViews(): Array<Class<out Enum<*>>> = arrayOf()
}

internal annotation class QueryViews