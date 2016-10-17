package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.exceptions

import com.aticosoft.appointments.mobile.business.domain.common.exception.PersistableObjectInfo
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event

/**
 * Created by Rodrigo Quesada on 17/10/16.
 */
/*internal*/ class IllegallyModifiedEvent(event: Event) : RuntimeException("Event was illegally modified: ${PersistableObjectInfo(event)}")