package com.aticosoft.appointments.mobile.business.domain.application.client

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
@Singleton
class ClientObserver @Inject constructor(services: ClientObserver.Services) : EntityObserver<Client>(services, Client::class.java) {

    protected class Services @Inject constructor(override val entityRepository: ClientRepository) : EntityObserver.Services<Client>()
}

@Singleton
internal class ClientListener @Inject constructor(services: EntityListener.Services) : EntityListener<Client>(services, Client::class.java)