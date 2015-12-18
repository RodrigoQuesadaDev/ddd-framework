package com.aticosoft.appointments.mobile.business.domain.application.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class ConfigurationServices @Inject protected constructor(
        context: ApplicationServices.Context,
        private val repository: ConfigurationRepository,
        private val queries: ConfigurationQueries
) : ApplicationServices(context) {

    class ChangeMaxConcurrentAppointments(val value: Int) : Command()

    fun execute(command: ChangeMaxConcurrentAppointments) = command.execute {
        repository.find(queries.retrieve())!!.let { configuration ->
            configuration.maxConcurrentAppointments = value
        }
    }
}