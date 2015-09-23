package com.aticosoft.appointments.mobile.business.domain.specs.common.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepositoryWrapper
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 27/09/15.
 */
internal abstract class EntityServicesAware<R : JdoRepository<E, Q>, E : Entity, Q : Repository.Queries> {

    @Inject protected lateinit var origRepository: R

    @Inject protected lateinit var testRepositoryFactory: TestRepositoryWrapper.Factory

    val repository by lazy { testRepositoryFactory.create(origRepository) }
}