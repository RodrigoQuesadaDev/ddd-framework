package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.JdoEventRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
@Singleton
internal class TestEventRepositoryManager<E : Event> @Inject protected constructor() : TestRepositoryManager<E, Long, JdoEventRepository<E>>()