package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.QConfiguration
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class JdoConfigurationQueries @Inject protected constructor(private val context: PersistenceContext) : ConfigurationQueries {

    override fun retrieve() = UniqueQuery { context.queryFactory.selectFrom(QConfiguration.configuration).fetchFirst() }
}