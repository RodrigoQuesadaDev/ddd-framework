package com.aticosoft.appointments.mobile.business.domain.application.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
class ConfigurationObserver @Inject protected constructor(services: EntityObserver.Services, entityRepository: ConfigurationRepository) : EntityObserver<Configuration>(services, entityRepository, Configuration::class.java)