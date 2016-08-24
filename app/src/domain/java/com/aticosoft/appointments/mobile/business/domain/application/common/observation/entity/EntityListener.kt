package com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
@Singleton
/*internal*/ class EntityListener<E : Entity> @Inject protected constructor(
        s: Services,
        objectType: Class<E>
) : PersistableObjectListener<E, String>(s, objectType)