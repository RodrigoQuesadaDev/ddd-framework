package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity

/**
 * Created by Rodrigo Quesada on 02/11/15.
 */
@Suppress("NOTHING_TO_INLINE")
/*internal*/ class EntityObservationFilter<E : Entity>(
        val entityType: Class<E>,
        vararg eventTypes: EventType = EventType.values(),
        private val criteria: (E) -> Boolean = { true }
) : (EntityChangeEvent<*>) -> Boolean {

    private val eventTypes: Array<out EventType>? = if (eventTypes.distinct().size == EventType.values().size) null else eventTypes

    override fun invoke(entityChange: EntityChangeEvent<*>) = with(entityChange) {
        hasSameEntityType() && matchesEventTypes() && (currentValue.matchesCriteria() || previousValue.matchesCriteria())
    }

    //TODO wrong warning says "Unnecessary safe call on a non-null receiver..." KT-9893
    private inline fun EntityChangeEvent<*>.hasSameEntityType() = currentValue?.javaClass ?: previousValue.javaClass == entityType

    private inline fun EntityChangeEvent<*>.matchesEventTypes() = eventTypes?.any { it == type } ?: true

    @Suppress("UNCHECKED_CAST")
    private inline fun Entity?.matchesCriteria() = this?.let { criteria(it as E) } ?: false
}