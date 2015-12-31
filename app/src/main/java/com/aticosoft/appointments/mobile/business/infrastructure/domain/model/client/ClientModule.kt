package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.create
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class ClientModule : EntityModule {
    companion object : EntityModule.CompanionObject {
        override val entityType = Client::class.java

        override val validators = emptyArray<EntityValidator<*>>()

        override fun EntityInitializer.Factory.create() = create<Client> { inject(it) }
    }

    @Provides @Singleton
    fun provideClientQueries(clientQueries: JdoClientQueries): ClientQueries = clientQueries

    @Provides @Singleton
    fun provideClientRepository(clientRepository: JdoClientRepository): ClientRepository = clientRepository

    interface EntityInjection {
        fun inject(entity: Client)
    }
}