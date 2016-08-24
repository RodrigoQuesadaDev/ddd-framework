package com.aticosoft.appointments.mobile.business.domain.application.common.observation.event

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObserver
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 21/08/16.
 */
@Singleton
/*internal*/ open class EventObserver<E : Event> @Inject protected constructor() : PersistableObjectObserver<E, Long, EventRepository<E>>()