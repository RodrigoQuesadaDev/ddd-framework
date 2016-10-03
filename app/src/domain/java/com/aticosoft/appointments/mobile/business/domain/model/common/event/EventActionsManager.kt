@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 30/09/16.
 */
@Singleton
/*internal*/ class EventActionsManager @Inject protected constructor(
        private val eventActionStateRepository: EventActionStateRepository,
        private val queries: Queries,
        eventActions: MutableSet<EventAction<*>>
) {
    //private val actionStatesByType = eventActionStateRepository.find(queries.allSorted()).groupBy { it.type }
    private val actionsMap = eventActions.groupBy { it.eventType }.mapValues { it.value/*.sort*/ }
    private val simpleActionsMap = actionsMap.filterValuesAreInstance(SimpleEventAction::class.java)
    private val overridableActionsMap = actionsMap.filterValuesAreInstance(OverridableEventAction::class.java)

    fun <E : Event> simpleActionsFor(eventType: Class<E>): List<SimpleEventAction<E>> = eventType.actionsFrom(simpleActionsMap)

    fun <E : Event> overridableActionsFor(eventType: Class<E>): List<OverridableEventAction<E>> = eventType.actionsFrom(overridableActionsMap)

    private inline fun <E : Event, A : EventAction<E>> Class<E>.actionsFrom(map: Map<Class<out Event>, List<EventAction<*>>>): List<A> {
        @Suppress("UNCHECKED_CAST")
        return map[this] as? List<A> ?: emptyList()
    }

    //region Queries
    @Singleton
    class Queries @Inject protected constructor() : JdoQueries<EventActionState>() {

        fun allSorted(): ListQuery<EventActionState> = ListQuery {
            val e = QEventActionState.eventActionState
            context.queryFactory.selectFrom(e).orderBy(e.priority.desc(), e.id.asc()).fetch()
        }
    }
//endregion
}

//region Utils
private inline fun <K, reified V> Map<K, List<*>>.filterValuesAreInstance(type: Class<V>): Map<K, List<V>> {
    return mapValues { it.value.filterIsInstance<V>() }
}
//endregion