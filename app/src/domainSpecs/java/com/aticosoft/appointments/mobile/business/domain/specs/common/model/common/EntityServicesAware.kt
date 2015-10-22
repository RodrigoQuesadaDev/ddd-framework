package com.aticosoft.appointments.mobile.business.domain.specs.common.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 27/09/15.
 */
internal abstract class EntityServicesAware<R : JdoRepository<E>, E : Entity> {

    @Inject protected lateinit var origRepository: R

    @Inject protected lateinit var testRepositoryFactory: TestRepository.Factory

    val repository by lazy { testRepositoryFactory.create(origRepository) }
}