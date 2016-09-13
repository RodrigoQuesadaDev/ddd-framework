package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 12/09/16.
 */
@PersistenceCapable
internal class DeleteMeEvent : Event()
//TODO this event should be removed when there is at least 1 real event for this app, it is used so
// that QEvent is generated (because os of right now the Event class cannot be annotated as PersistenceCapable)