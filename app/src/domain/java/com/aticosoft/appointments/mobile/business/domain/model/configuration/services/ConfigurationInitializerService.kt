package com.aticosoft.appointments.mobile.business.domain.model.configuration.services

import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationFactory
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class ConfigurationInitializerService @Inject protected constructor(
        private val configurationFactory: ConfigurationFactory,
        private val configurationRepository: Repository<Configuration>,
        private val configurationQueries: ConfigurationQueries
) {

    fun run() {
        var configuration = configurationRepository.find(configurationQueries.RETRIEVE)
        if (configuration == null) {
            configuration = configurationFactory.create()
            configurationRepository.add(configuration)
        }
    }
}