package com.aticosoft.appointments.mobile.business.domain.application

import com.aticosoft.appointments.mobile.business.domain.application.ClientServices.AddClient
import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import org.joda.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 02/10/15.
 */
/*internal*/ class ClientServices @Inject constructor(
        val addClient: AddClient
) {

    @Singleton
    class AddClient @Inject constructor(
            services: ApplicationService.Services,
            private val identityGenerator: IdentityGenerator,
            private val clientRepository: ClientRepository
    ) : ApplicationService<AddClient.Command>(services) {

        class Command(
                val name: String,
                val birthDate: LocalDate
        ) : ApplicationCommand

        override fun doExecute(command: Command) {
            with(command) {
                clientRepository.add(Client(
                        identityGenerator.generate(),
                        name = name,
                        birthDate = birthDate
                ))
            }
        }
    }
}