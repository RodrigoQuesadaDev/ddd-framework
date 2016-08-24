package com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObserver
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 21/08/16.
 */
@Singleton
/*internal*/ open class EntityObserver<E : Entity> @Inject protected constructor() : PersistableObjectObserver<E, String, EntityRepository<E>>()