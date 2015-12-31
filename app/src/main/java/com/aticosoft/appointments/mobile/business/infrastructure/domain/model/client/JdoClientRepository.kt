package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.domain.model.client.QClient
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 27/09/15.
 */
@Singleton
/*internal*/ class JdoClientRepository @Inject protected constructor(context: PersistenceContext) : JdoRepository<Client>(context, QClient.client), ClientRepository