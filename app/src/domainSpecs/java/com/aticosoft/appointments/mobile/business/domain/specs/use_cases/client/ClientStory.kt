package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.specs.SpecApplication
import com.aticosoft.appointments.mobile.business.domain.specs.SpecApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientStory.SpecApplicationImpl
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 05/10/15.
 */
@Config(application = SpecApplicationImpl::class)
internal abstract class ClientStory : DomainStory() {

    class SpecApplicationImpl : SpecApplication<ClientStory>(SpecApplicationComponent::inject)

    @Inject protected lateinit var clientSteps: ClientSteps

    override val steps by lazy { arrayOf(clientSteps) }
}