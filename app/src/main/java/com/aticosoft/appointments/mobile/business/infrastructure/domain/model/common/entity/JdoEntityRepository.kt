package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
import com.querydsl.core.types.EntityPath

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
/*internal*/ abstract class JdoEntityRepository<E : Entity> protected constructor(entityPath: EntityPath<E>) : JdoRepository<E, String>(entityPath), EntityRepository<E>
