package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
import com.querydsl.core.types.EntityPath

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
/*internal*/ abstract class JdoEventRepository<E : Event> protected constructor(entityPath: EntityPath<E>) : JdoRepository<E, Long>(entityPath)