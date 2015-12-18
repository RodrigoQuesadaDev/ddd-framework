package com.aticosoft.appointments.mobile.business.domain.application.client

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
@Singleton
class ClientObserver @Inject protected constructor(services: EntityObserver.Services, clientRepository: ClientRepository) : EntityObserver<Client>(services, clientRepository, Client::class.java)