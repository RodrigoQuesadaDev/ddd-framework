package com.aticosoft.appointments.mobile.business.domain.specs.common.model.client

import com.aticosoft.appointments.mobile.business.domain.application.ClientServices
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepositoryWrapper
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.common.EntityServicesAware
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoClientRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 27/09/15.
 */
internal interface ClientServicesAware {

    val clientServices: ClientServices
    val clientRepository: TestRepositoryWrapper<JdoClientRepository, Client, ClientQueries>
}

internal class ClientServicesAwareImpl @Inject constructor() : EntityServicesAware<JdoClientRepository, Client, ClientQueries>(), ClientServicesAware {

    @Inject lateinit override var clientServices: ClientServices
    override val clientRepository by lazy { repository }
}