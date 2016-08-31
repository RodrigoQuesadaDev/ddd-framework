package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.JdoEventRepository

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ interface JdoEventModule<E : Event, out Q : Queries<E>> : EventModule<E, Q, JdoEventRepository<E>>