package com.aticosoft.appointments.mobile.business.domain.application.client

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import org.joda.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 02/10/15.
 */
@Singleton
/*internal*/ class ClientServices @Inject constructor(
        context: ApplicationServices.Context,
        private val entityContext: Entity.Context,
        private val clientRepository: ClientRepository
) : ApplicationServices(context) {

    class AddClient(val name: String, val birthDate: LocalDate) : Command()

    fun execute(command: AddClient) = command.execute {
        clientRepository.add(Client(
                entityContext, name = name, birthDate = birthDate
        ))
    }
}