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
    private val actionsByEvent: Map<Class<out Event>, List<EventAction<*>>> = eventActions.groupBy { it.eventType }
    /*.mapValues {
        val eventType = it.key
        val actions = it.value

        val actionStatesByType = eventActionStateRepository.find(queries.sortedFor(eventType))
        it.value
    }*/

    private val simpleActionsMap = actionsByEvent.filterValuesAreInstance(SimpleEventAction::class.java)
    private val overridableActionsMap = actionsByEvent.filterValuesAreInstance(OverridableEventAction::class.java)

    fun <E : Event> simpleActionsFor(eventType: Class<E>): List<SimpleEventAction<E>> = eventType.actionsFrom(simpleActionsMap)

    fun <E : Event> overridableActionsFor(eventType: Class<E>): List<OverridableEventAction<E>> = eventType.actionsFrom(overridableActionsMap)

    private inline fun <E : Event, A : EventAction<E>> Class<E>.actionsFrom(map: Map<Class<out Event>, List<EventAction<*>>>): List<A> {
        @Suppress("UNCHECKED_CAST")
        return map[this] as? List<A> ?: emptyList()
    }

    //region Queries
    @Singleton
    class Queries @Inject protected constructor() : JdoQueries<EventActionState>() {

        fun sortedFor(eventType: Class<out Event>): ListQuery<EventActionState> = ListQuery {
            with(EventActionType.Companion) {
                val e = QEventActionState.eventActionState
                context.queryFactory.selectFrom(e)
                        .where(e.type.eventType.eq(eventType.typeString))
                        .orderBy(e.priority.desc(), e.id.asc())
                        .fetch()
            }
        }
    }
//endregion
}

//region Utils
private inline fun <K, reified V> Map<K, List<*>>.filterValuesAreInstance(type: Class<V>): Map<K, List<V>> {
    return mapValues { it.value.filterIsInstance<V>() }
}
//endregion