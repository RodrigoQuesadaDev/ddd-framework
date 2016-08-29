package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
@Singleton
internal class TestEntityRepositoryManager<E : Entity> @Inject protected constructor() : TestRepositoryManager<E, String, JdoEntityRepository<E>>()