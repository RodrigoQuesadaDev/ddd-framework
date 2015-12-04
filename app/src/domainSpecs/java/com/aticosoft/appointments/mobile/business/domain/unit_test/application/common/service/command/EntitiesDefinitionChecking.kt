package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import  com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.EntitiesDefinitionChecking.TestApplicationImpl
import dagger.Component
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class EntitiesDefinitionChecking : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<EntitiesDefinitionChecking>

    class TestApplicationImpl : TestApplication(DaggerEntitiesDefinitionChecking_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
    ) {
    }
}