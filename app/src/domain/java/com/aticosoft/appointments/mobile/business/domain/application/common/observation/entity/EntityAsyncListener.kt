package com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
@Singleton
/*internal*/ class EntityAsyncListener<E : Entity> @Inject protected constructor() : PersistableObjectAsyncListener<E, String>()