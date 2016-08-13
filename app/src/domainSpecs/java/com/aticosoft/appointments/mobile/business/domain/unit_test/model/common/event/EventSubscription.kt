package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.EventSubscription.UnitTestApplicationImpl
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 11/08/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventSubscription : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventSubscription>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(

    ) {
        @When("event A occurs")
        @Pending
        fun whenAnEventOfTypeAOccurs() {
        }

        @Then("no exception is thrown")
        @Pending
        fun thenNoExceptionIsThrown() {
        }
    }
}