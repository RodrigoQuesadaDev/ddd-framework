package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling

import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseApplication
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.OwnerSchedulesAppointment.UnitTestApplicationImpl
import org.robolectric.annotation.Config

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class OwnerSchedulesAppointment : AppointmentStory() {

    class UnitTestApplicationImpl : UseCaseApplication<OwnerSchedulesAppointment>(UseCaseApplicationComponent::inject)
}