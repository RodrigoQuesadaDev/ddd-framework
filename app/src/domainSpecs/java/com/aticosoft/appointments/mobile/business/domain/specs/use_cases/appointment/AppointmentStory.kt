package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.specs.common.DomainStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentStory.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientSteps
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import dagger.Component
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 17/09/15.
 */
@Config(application = TestApplicationImpl::class)
internal abstract class AppointmentStory : DomainStory() {

    @Inject protected lateinit var appointmentSteps: AppointmentSteps
    @Inject protected lateinit var clientSteps: ClientSteps

    @Inject protected lateinit var appointmentTimeConverter: AppointmentTimeConverter

    override val storyConverters by lazy { arrayOf<ParameterConverter>(appointmentTimeConverter) }

    override val steps by lazy { arrayOf(appointmentSteps, clientSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<AppointmentStory>

    class TestApplicationImpl() : TestApplication(DaggerAppointmentStory_TestApplicationComponentImpl::class)
}