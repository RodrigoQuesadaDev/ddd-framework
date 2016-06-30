package com.aticosoft.appointments.mobile.business.domain.specs.use_cases

import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.OwnerSchedulesAppointment
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.constrains.TimeSlotsAlignment
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.constrains.TimeSlotsSpace
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
internal interface UseCaseApplicationComponent : TestApplicationComponent {

    fun inject(test: OwnerSchedulesAppointment)
    fun inject(test: TimeSlotsAlignment)
    fun inject(test: TimeSlotsSpace)

    @Component.Builder
    interface Builder : TestApplicationComponent.Builder<UseCaseApplicationComponent, Builder>
}

internal abstract class UseCaseApplication<S : UseCaseStory>(
        injectTest: UseCaseApplicationComponent.(S) -> Unit
) : TestApplication<S, UseCaseApplicationComponent, UseCaseApplicationComponent.Builder>({ DaggerUseCaseApplicationComponent.builder() }, injectTest)