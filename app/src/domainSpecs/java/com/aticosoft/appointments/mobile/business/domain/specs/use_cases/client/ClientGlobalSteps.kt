package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client

import com.aticosoft.appointments.mobile.business.domain.application.client.ClientServices
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientServices.AddClient
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.When
import org.joda.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 28/09/15.
 */
@Singleton
internal class ClientGlobalSteps @Inject constructor(
        private val clientRepositoryManager: TestEntityRepositoryManager<Client>,
        private val clientServices: ClientServices
) {
    @Given("no clients")
    fun givenNoClients() {
        clientRepositoryManager.clear()
    }

    @When("the next clients are added: \$clients")
    fun whenTheNextClientsAreAdded(clients: MutableList<ClientExample>) {
        clients.forEach {
            whenTheOwnerAddsAClient(it.name, it.birthDate)
        }
    }

    @When("the owner adds a client with name \$name and birthDate \$birthDate")
    fun whenTheOwnerAddsAClient(name: String, birthDate: LocalDate) {
        clientServices.execute(AddClient(name, birthDate))
    }
}

@AsParameters
internal class ClientExample {
    lateinit var name: String
    lateinit var birthDate: LocalDate
}