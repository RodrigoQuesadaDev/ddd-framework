package com.aticosoft.appointments.mobile.business.domain.application.client

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 19/10/15.
 */
@Singleton
class ClientObserver @Inject constructor(services: EntityObserver.Services, repository: ClientRepository) : EntityObserver<Client>(services, repository, Client::class)