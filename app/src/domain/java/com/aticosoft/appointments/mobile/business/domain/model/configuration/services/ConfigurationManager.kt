package com.aticosoft.appointments.mobile.business.domain.model.configuration.services

import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 24/06/16.
 */
@Singleton
/*internal*/ class ConfigurationManager @Inject protected constructor(
        val configurationRepository: Repository<Configuration>,
        val configurationQueries: ConfigurationQueries
) {

    fun retrieve(): Configuration = configurationRepository.find(configurationQueries.RETRIEVE)!!
}