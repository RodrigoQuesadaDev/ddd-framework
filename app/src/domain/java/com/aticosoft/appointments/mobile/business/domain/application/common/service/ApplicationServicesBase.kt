package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.model.configuration.services.ConfigurationManager
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 21/12/15.
 */
/*internal*/ abstract class ApplicationServicesBase(
        private val c: Context
) : ApplicationServices(c.superContext) {

    protected fun retrieveConfiguration() = c.configurationManager.retrieve()

    class Context @Inject protected constructor(
            val superContext: ApplicationServices.Context,
            val configurationManager: ConfigurationManager
    )
}