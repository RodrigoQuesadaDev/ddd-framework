package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
@Singleton
/*internal*/ class JdoEntityRepository<E : Entity> @Inject protected constructor() : JdoRepository<E, String>(), EntityRepository<E>
