package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoEventModule

/**
 * Created by Rodrigo Quesada on 27/06/16.
 */
// Test event modules do not need to define abstract query views
internal abstract class TestEventModule<E : Event> : JdoEventModule<E, Queries<E>>