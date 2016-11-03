package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueryView
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
internal class ConfigurationModule : JdoRootEntityModule<Configuration, ConfigurationQueries> {

    @Provides
    override fun provideType(): Class<Configuration> = Configuration::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @Singleton
    fun provideQueries(queries: JdoConfigurationQueries): ConfigurationQueries = queries

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = ConfigurationQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<Configuration>): EntityRepository<Configuration> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<Configuration>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityAsyncListener<Configuration>): PersistableObjectAsyncListener<*, *> = listener
}