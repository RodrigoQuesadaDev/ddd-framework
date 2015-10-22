package com.aticosoft.appointments.mobile.business.domain.specs.common.model.client

import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientServices
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepository
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.common.EntityServicesAware
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoClientRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 27/09/15.
 */
internal interface ClientServicesAware {

    val clientServices: ClientServices
    val clientObserver: ClientObserver
    val clientQueries: ClientQueries
    val clientRepository: TestRepository<JdoClientRepository, Client>
}

internal class ClientServicesAwareImpl @Inject constructor() : EntityServicesAware<JdoClientRepository, Client>(), ClientServicesAware {

    @Inject lateinit override var clientServices: ClientServices
    @Inject lateinit override var clientObserver: ClientObserver
    @Inject lateinit override var clientQueries: ClientQueries
    override val clientRepository by lazy { repository }
}