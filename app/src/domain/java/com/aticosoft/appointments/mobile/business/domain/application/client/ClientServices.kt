package com.aticosoft.appointments.mobile.business.domain.application.client

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientFactory
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import org.joda.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 02/10/15.
 */
@Singleton
/*internal*/ class ClientServices @Inject protected constructor(
        context: ApplicationServices.Context,
        private val clientFactory: ClientFactory,
        private val clientRepository: ClientRepository
) : ApplicationServices(context) {

    class AddClient(val name: String, val birthDate: LocalDate) : Command()

    fun execute(command: AddClient) = command.execute {
        clientRepository.add(clientFactory.create(name, birthDate))
    }
}