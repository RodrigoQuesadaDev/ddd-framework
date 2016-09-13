package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices.ScheduleAppointment
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.rx.testing.firstEvent
import com.rodrigodev.common.rx.testing.testSubscribe
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.joda.time.Interval
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
@Singleton
internal class AppointmentGlobalSteps @Inject protected constructor(
        private val appointmentRepositoryManager: TestEntityRepositoryManager<Appointment>,
        private val appointmentServices: AppointmentServices,
        private val clientObserver: EntityObserver<Client>,
        private val clientQueries: ClientQueries
) : SpecSteps() {

    @Inject protected lateinit var appointmentTimeConverter: AppointmentTimeConverter

    override val converters by lazy { arrayOf<ParameterConverters.ParameterConverter>(appointmentTimeConverter) }

    var thrownScheduleException: Throwable? = null
        private set
    private var scheduleExceptionTypes: MutableList<Class<out Exception>> = mutableListOf()

    @Given("no appointments scheduled")
    fun givenNoAppointmentsScheduled() {
        appointmentRepositoryManager.clear()
    }

    @When("the owner schedules an appointment for \$client on \$time")
    fun whenTheOwnerSchedulesAnAppointmentFor(client: String, time: Interval) {
        val clientsResult = clientObserver.observe(clientQueries.nameLike(client)).testSubscribe().firstEvent()
        thrownScheduleException = catchThrowable(scheduleExceptionTypes) { appointmentServices.execute(ScheduleAppointment(clientsResult.first().id, time)) }
    }

    //region Exception Catching
    fun catchScheduleExceptionsOfType(vararg types: Class<out Exception>) {
        scheduleExceptionTypes.addAll(types)
    }
    //endregion
}