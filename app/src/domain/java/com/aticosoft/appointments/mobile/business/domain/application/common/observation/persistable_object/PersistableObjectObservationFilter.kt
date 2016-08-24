package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject

/**
 * Created by Rodrigo Quesada on 02/11/15.
 */
@Suppress("NOTHING_TO_INLINE")
/*internal*/ class PersistableObjectObservationFilter<P : PersistableObject<*>>(
        val objectType: Class<P>,
        vararg eventTypes: EventType = EventType.values(),
        private val criteria: (P) -> Boolean = { true }
) : (PersistableObjectChangeEvent<*>) -> Boolean {

    private val eventTypes: Array<out EventType>? = if (eventTypes.distinct().size == EventType.values().size) null else eventTypes

    override fun invoke(objectChange: PersistableObjectChangeEvent<*>) = with(objectChange) {
        hasSameObjectType() && matchesEventTypes() && (currentValue.matchesCriteria() || previousValue.matchesCriteria())
    }

    private inline fun PersistableObjectChangeEvent<*>.hasSameObjectType() = (currentValue?.javaClass ?: previousValue?.javaClass) == objectType

    private inline fun PersistableObjectChangeEvent<*>.matchesEventTypes() = eventTypes?.any { it == type } ?: true

    @Suppress("UNCHECKED_CAST")
    private inline fun PersistableObject<*>?.matchesCriteria() = this?.let { criteria(it as P) } ?: false
}