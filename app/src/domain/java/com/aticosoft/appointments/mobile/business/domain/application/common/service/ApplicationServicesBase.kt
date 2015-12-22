package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServicesBase.Context
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationRepository
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 21/12/15.
 */
/*internal*/ abstract class ApplicationServicesBase(
        private val c: Context
) : ApplicationServices(c.superContext) {

    protected fun retrieveConfiguration() = c.configurationRepository.find(c.configurationQueries.RETRIEVE)!!

    class Context @Inject protected constructor(
            val superContext: ApplicationServices.Context,
            val configurationRepository: ConfigurationRepository,
            val configurationQueries: ConfigurationQueries
    )
}