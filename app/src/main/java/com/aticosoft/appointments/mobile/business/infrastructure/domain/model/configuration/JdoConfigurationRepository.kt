package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationRepository
import com.aticosoft.appointments.mobile.business.domain.model.configuration.QConfiguration
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepositoryBase
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class JdoConfigurationRepository @Inject protected constructor(context: PersistenceContext) : JdoRepositoryBase<Configuration>(context, QConfiguration.configuration), ConfigurationRepository