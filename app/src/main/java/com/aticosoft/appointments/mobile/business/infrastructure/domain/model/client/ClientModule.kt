package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueryView
import com.aticosoft.appointments.mobile.business.domain.model.client.validation.ClientValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.RootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class ClientModule : RootEntityModule<Client,
        ClientQueries, JdoClientQueries,
        ClientQueryView,
        JdoClientRepository,
        ClientValidator<*>,
        EntityInitializer<Client>,
        EntityListener<Client>> {

    @Provides
    override fun provideEntityType(): Class<Client> = Client::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @Singleton
    override fun provideQueries(queries: JdoClientQueries): ClientQueries = queries

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = ClientQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoClientRepository): Repository<Client> = repository

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<Client>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<Client>): EntityListener<*> = listener
}