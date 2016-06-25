package com.aticosoft.appointments.mobile.business.domain.specs

import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.OwnerSchedulesAppointment
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.constrains.TimeSlotsAlignment
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Singleton
@Component(modules = arrayOf(TestApplicationModule::class))
internal interface SpecApplicationComponent : TestApplicationComponent {

    fun inject(test: OwnerSchedulesAppointment)
    fun inject(test: TimeSlotsAlignment)
    fun inject(test: ClientStory)

    @Component.Builder
    interface Builder : TestApplicationComponent.Builder<SpecApplicationComponent, Builder>
}

internal abstract class SpecApplication<S : DomainStory>(
        injectTest: SpecApplicationComponent.(S) -> Unit
) : TestApplication<S, SpecApplicationComponent, SpecApplicationComponent.Builder>({ DaggerSpecApplicationComponent.builder() }, injectTest)