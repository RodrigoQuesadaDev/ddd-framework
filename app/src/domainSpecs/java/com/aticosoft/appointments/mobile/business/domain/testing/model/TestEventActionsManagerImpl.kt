package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionsManagerImpl
import com.rodrigodev.common.collection.shuffle
import com.rodrigodev.common.properties.Delegates.atomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 19/10/16.
 */
@Singleton
internal class TestEventActionsManagerImpl @Inject protected constructor() : EventActionsManagerImpl() {

    private var randomEventActionsOrder by atomicBoolean()

    fun randomizeEventActionsOrder() {
        randomEventActionsOrder = true
    }

    override fun sortActions(actions: List<EventAction<*>>)
            = if (randomEventActionsOrder) actions.shuffle() else super.sortActions(actions)
}