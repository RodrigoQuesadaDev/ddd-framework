package com.aticosoft.appointments.mobile.business.domain.specs.stories.client

import com.aticosoft.appointments.mobile.business.domain.application.ClientServices.AddClient
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.client.ClientServicesAware
import com.aticosoft.appointments.mobile.business.domain.specs.stories.DomainStory
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.LocalDate

/**
 * Created by rodrigo on 28/09/15.
 */
internal class ClientSteps(domainStory: DomainStory) : ClientServicesAware by domainStory {

    @Given("no clients")
    fun noClients() {
        clientRepository.clear()
    }

    @When("the next clients are added: \$clients")
    fun theNextClientsAreAdded(clients: MutableList<ClientExample>) {
        clients.forEach {
            theOwnerAddsAClient(it.name, it.birthDate)
        }
    }

    @When("the owner adds a client with name \$name and birthDate \$birthDate")
    fun theOwnerAddsAClient(name: String, birthDate: LocalDate) {
        clientServices.addClient.execute(AddClient.Command(name, birthDate))
    }

    @Then("\$n clients were added")
    fun nClientsWereAdded(n: Long) {
        assertThat(clientRepository.size()).isEqualTo(n)
    }

    @Then("a client with name \$name and birthDate \$birthDate exists in the system")
    fun aClientExistsInTheSystem(name: String, birthDate: LocalDate) {
        val clientsResult = clientRepository.find(clientRepository.QUERIES.nameLike(name))
        assertThat(clientsResult.filter { it.birthDate == birthDate }).isNotEmpty()
    }
}

@AsParameters
private class ClientExample {
    lateinit var name: String
    lateinit var birthDate: LocalDate
}