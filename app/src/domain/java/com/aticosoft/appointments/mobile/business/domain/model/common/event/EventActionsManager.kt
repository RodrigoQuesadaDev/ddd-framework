@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionType.Companion.typeString
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.identifiedBy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 30/09/16.
 */
@Singleton
/*internal*/ interface EventActionsManager {

    fun <E : Event> simpleActionsFor(eventType: Class<E>): List<SimpleEventAction<E>>

    fun <E : Event> overridableActionsFor(eventType: Class<E>): List<OverridableEventAction<E>>
}

@Singleton
/*internal*/ open class EventActionsManagerImpl @Inject protected constructor() : EventActionsManager {

    private lateinit var m: InjectedMembers

    private val actionsByEvent: Map<Class<out Event>, List<EventAction<*>>> by lazy {
        with(m) {
            eventActions.groupBy { it.eventType }
                    .mapValues {
                        val eventType = it.key
                        val actions = it.value
                        val actionsByType = actions.identifiedBy { it.typeString }

                        eventType.loadSortedActionStates(actions)
                                .map { actionsByType[it.type.actionType]!! }
                    }
        }
    }

    private val simpleActionsMap by lazy { actionsByEvent.filterValuesAreInstance(SimpleEventAction::class.java) }
    private val overridableActionsMap by lazy { actionsByEvent.filterValuesAreInstance(OverridableEventAction::class.java) }

    private fun init() = with(m) {
        eventActions.forEach { it.init() }
    }

    override fun <E : Event> simpleActionsFor(eventType: Class<E>): List<SimpleEventAction<E>> = eventType.actionsFrom(simpleActionsMap)

    override fun <E : Event> overridableActionsFor(eventType: Class<E>): List<OverridableEventAction<E>> = eventType.actionsFrom(overridableActionsMap)

    private inline fun <E : Event, A : EventAction<E>> Class<E>.actionsFrom(map: Map<Class<out Event>, List<EventAction<*>>>): List<A> {
        @Suppress("UNCHECKED_CAST")
        return map[this] as? List<A> ?: emptyList()
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
        init()
    }

    class InjectedMembers @Inject protected constructor(
            val persistenceContext: PersistenceContext,
            val eventActionStateRepository: EventActionStateRepository,
            val queries: EventActionStateQueries,
            val eventActions: MutableSet<EventAction<*>>
    )
    //endregion

    //region Actions Sorting
    private inline fun Class<out Event>.loadSortedActionStates(actions: List<EventAction<*>>): List<EventActionState> = with(m) {
        persistenceContext.execute {
            if (eventActionStateRepository.count(queries.countBy(this@loadSortedActionStates)) == 0L) {
                actions.sort().forEachIndexed { i, action ->
                    eventActionStateRepository.add(EventActionState(action, i))
                }
            }
            eventActionStateRepository.find(queries.sortedFor(this@loadSortedActionStates))
        }
    }

    private inline fun List<EventAction<*>>.sort(): List<EventAction<*>> = sortActions(this)

    //TODO remove and leave only extension function after KT-7859 is resolved
    protected open fun sortActions(actions: List<EventAction<*>>): List<EventAction<*>> = actions.sortedByDescending { it.priority }
    //endregion
}

//region Utils
private inline fun <K, reified V> Map<K, List<*>>.filterValuesAreInstance(type: Class<V>): Map<K, List<V>> {
    return mapValues { it.value.filterIsInstance<V>() }
}
//endregion