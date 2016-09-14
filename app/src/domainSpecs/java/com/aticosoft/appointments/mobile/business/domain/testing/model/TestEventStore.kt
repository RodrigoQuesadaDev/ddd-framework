package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventStoreBase
import com.google.common.collect.Sets
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 01/09/16.
 */
@Singleton
internal class TestEventStore<E : Event> @Inject protected constructor() : EventStoreBase<E>() {

    val simpleActionsSet: MutableSet<SimpleEventAction<E>> by lazy { Sets.newConcurrentHashSet(super.simpleActions.toSet()) }

    override val simpleActions: Sequence<SimpleEventAction<E>>
        get() = simpleActionsSet.asSequence()
}