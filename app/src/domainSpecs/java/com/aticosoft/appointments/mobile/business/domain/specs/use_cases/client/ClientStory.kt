package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client

import com.aticosoft.appointments.mobile.business.domain.specs.common.DomainStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientStory.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import dagger.Component
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 05/10/15.
 */
@Config(application = TestApplicationImpl::class)
internal abstract class ClientStory : DomainStory() {

    @Inject protected lateinit var clientSteps: ClientSteps

    override val steps by lazy { arrayOf(clientSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ClientStory>

    class TestApplicationImpl : TestApplication(DaggerClientStory_TestApplicationComponentImpl::class)
}