package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client

import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientServices
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientServices.AddClient
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.ClientRepositoryManager
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.LocalDate
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 28/09/15.
 */
internal class ClientSteps @Inject constructor(
        private val clientRepositoryManager: ClientRepositoryManager,
        private val clientServices: ClientServices,
        private val clientObserver: ClientObserver,
        private val clientQueries: ClientQueries
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

    @Then("\$n clients were added")
    fun thenNClientsWereAdded(n: Long) {
        assertThat(clientObserver.observeTotalCount().testSubscribe().firstEvent()).isEqualTo(n)
    }

    @Then("a client with name \$name and birthDate \$birthDate exists in the system")
    fun thenAClientExistsInTheSystem(name: String, birthDate: LocalDate) {
        val clientsResult = clientObserver.observe(clientQueries.nameLike(name)).testSubscribe().firstEvent()
        assertThat(clientsResult.filter { it.birthDate == birthDate }).isNotEmpty()
    }
}

@AsParameters
internal class ClientExample {
    lateinit var name: String
    lateinit var birthDate: LocalDate
}