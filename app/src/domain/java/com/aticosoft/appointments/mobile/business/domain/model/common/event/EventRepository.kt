package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
/*internal*/ interface EventRepository<E : Event> : Repository<E, Long>
