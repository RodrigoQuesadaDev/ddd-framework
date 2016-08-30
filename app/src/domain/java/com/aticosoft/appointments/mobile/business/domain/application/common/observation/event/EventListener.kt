package com.aticosoft.appointments.mobile.business.domain.application.common.observation.event

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
@Singleton
/*internal*/ class EventListener<E : Event> @Inject protected constructor() : PersistableObjectListener<E, Long>()