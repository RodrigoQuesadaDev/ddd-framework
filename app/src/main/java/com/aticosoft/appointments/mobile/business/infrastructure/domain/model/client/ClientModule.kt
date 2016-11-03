package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
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
internal class ClientModule : JdoRootEntityModule<Client, ClientQueries> {

    @Provides
    override fun provideType(): Class<Client> = Client::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @Singleton
    fun provideQueries(queries: JdoClientQueries): ClientQueries = queries

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = ClientQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<Client>): EntityRepository<Client> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<Client>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityAsyncListener<Client>): PersistableObjectAsyncListener<*, *> = listener
}