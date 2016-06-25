package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices.ScheduleAppointment
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.AppointmentRepositoryManager
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.When
import org.joda.time.Interval
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
internal class AppointmentSteps @Inject constructor(
        private val appointmentRepositoryManager: AppointmentRepositoryManager,
        private val appointmentServices: AppointmentServices,
        private val clientObserver: EntityObserver<Client>,
        private val clientQueries: ClientQueries
) {

    @Given("no appointments scheduled")
    fun givenNoAppointmentsScheduled() {
        appointmentRepositoryManager.clear()
    }

    @When("the owner schedules an appointment for \$client on \$time")
    fun whenTheOwnerSchedulesAnAppointmentFor(client: String, time: Interval) {
        val clientsResult = clientObserver.observe(clientQueries.nameLike(client)).testSubscribe().firstEvent()
        appointmentServices.execute(ScheduleAppointment(clientsResult.first().id, time))
    }
}