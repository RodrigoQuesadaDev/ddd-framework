@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionType.Companion.typeString
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.CountQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.querydsl.jdo.JDOQuery
import com.rodrigodev.common.collection.identifiedBy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 30/09/16.
 */
@Singleton
/*internal*/ class EventActionsManager @Inject protected constructor(
        private val persistenceContext: PersistenceContext,
        private val eventActionStateRepository: EventActionStateRepository,
        private val queries: Queries,
        eventActions: MutableSet<EventAction<*>>
) {
    private val actionsByEvent: Map<Class<out Event>, List<EventAction<*>>> = eventActions.groupBy { it.eventType }
            .mapValues {
                val eventType = it.key
                val actions = it.value
                val actionsByType = actions.identifiedBy { it.typeString }

                eventType.loadSortedActionStates(actions)
                        .map { actionsByType[it.type.actionType]!! }
            }

    private val simpleActionsMap = actionsByEvent.filterValuesAreInstance(SimpleEventAction::class.java)
    private val overridableActionsMap = actionsByEvent.filterValuesAreInstance(OverridableEventAction::class.java)

    fun <E : Event> simpleActionsFor(eventType: Class<E>): List<SimpleEventAction<E>> = eventType.actionsFrom(simpleActionsMap)

    fun <E : Event> overridableActionsFor(eventType: Class<E>): List<OverridableEventAction<E>> = eventType.actionsFrom(overridableActionsMap)

    private inline fun <E : Event, A : EventAction<E>> Class<E>.actionsFrom(map: Map<Class<out Event>, List<EventAction<*>>>): List<A> {
        @Suppress("UNCHECKED_CAST")
        return map[this] as? List<A> ?: emptyList()
    }

    //region Actions Sorting
    private inline fun Class<out Event>.loadSortedActionStates(actions: List<EventAction<*>>): List<EventActionState> = persistenceContext.execute {
        if (eventActionStateRepository.count(queries.countBy(this)) == 0L) {
            actions.sortedByDescending { it.priority }
                    .forEachIndexed { i, action ->
                        eventActionStateRepository.add(EventActionState(action, i))
                    }
        }
        eventActionStateRepository.find(queries.sortedFor(this))
    }
    //endregion

    //region Queries
    @Singleton
    class Queries @Inject protected constructor() : JdoQueries<EventActionState>() {

        fun countBy(eventType: Class<out Event>): CountQuery<EventActionState>
                = CountQuery { jdoQueryBy(eventType).fetchCount() }

        fun sortedFor(eventType: Class<out Event>): ListQuery<EventActionState> = ListQuery {
            val e = QEventActionState.eventActionState
            jdoQueryBy(eventType)
                    .orderBy(e.position.asc())
                    .fetch()
        }

        private inline fun jdoQueryBy(eventType: Class<out Event>): JDOQuery<EventActionState> {
            val e = QEventActionState.eventActionState
            return context.queryFactory.selectFrom(e).where(e.type.eventType.eq(eventType.typeString))
        }
    }
//endregion
}

//region Utils
private inline fun <K, reified V> Map<K, List<*>>.filterValuesAreInstance(type: Class<V>): Map<K, List<V>> {
    return mapValues { it.value.filterIsInstance<V>() }
}
//endregion