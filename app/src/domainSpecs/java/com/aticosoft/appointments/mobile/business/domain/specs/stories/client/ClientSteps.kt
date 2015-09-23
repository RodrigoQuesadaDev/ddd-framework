package com.aticosoft.appointments.mobile.business.domain.specs.stories.client

import com.aticosoft.appointments.mobile.business.domain.application.ClientServices.AddClient
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.client.ClientServicesAware
import com.aticosoft.appointments.mobile.business.domain.specs.stories.DomainStory
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When

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
            clientServices.addClient.execute(AddClient.Command(it.name))
        }
    }

    @Then("\$n clients were added")
    fun nClientsWereAdded(n: Long) {
        assertThat(clientRepository.size()).isEqualTo(n)
    }
}

@AsParameters
private class ClientExample {
    lateinit var name: String
}