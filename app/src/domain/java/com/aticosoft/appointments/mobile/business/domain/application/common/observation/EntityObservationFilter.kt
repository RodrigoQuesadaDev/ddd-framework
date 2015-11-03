package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity

/**
 * Created by Rodrigo Quesada on 02/11/15.
 */
/*internal*/ class EntityObservationFilter<E : Entity>(
        val entityType: Class<E>,
        vararg eventTypes: EventType = EventType.values,
        private val criteria: (E) -> Boolean = { true }
) : (EntityChangeEvent<*>) -> Boolean {

    private val eventTypes: Array<out EventType>? = if (eventTypes.distinct().size == EventType.values.size) null else eventTypes

    @Suppress("UNCHECKED_CAST")
    override fun invoke(entityChange: EntityChangeEvent<*>) = with(entityChange) {
        entity.javaClass == entityType
                && eventTypes?.any { it == type } ?: true
                && criteria(entity as E)
    }
}