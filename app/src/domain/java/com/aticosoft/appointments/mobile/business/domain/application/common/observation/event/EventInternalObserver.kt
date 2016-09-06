package com.aticosoft.appointments.mobile.business.domain.application.common.observation.event

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObserverBase
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/09/16.
 */
@Singleton
/*internal*/ class EventInternalObserver<E : Event> @Inject protected constructor() : PersistableObjectObserverBase<E, Long, EventRepository<E>>()