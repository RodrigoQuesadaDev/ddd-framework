package com.aticosoft.appointments.mobile.business.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.configuration.services.ConfigurationInitializerService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class ModelInitializerService @Inject protected constructor(
        private val configurationInitializerService: ConfigurationInitializerService
) {
    fun run() = configurationInitializerService.run()
}