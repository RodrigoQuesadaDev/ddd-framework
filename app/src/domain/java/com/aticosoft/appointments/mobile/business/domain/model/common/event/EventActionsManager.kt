@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionType.Companion.typeString
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.identifiedBy
import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.properties.UnsafePostInitialized
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 30/09/16.
 */
@Singleton
/*internal*/  class EventActionsManager @Inject protected constructor() : UnsafePostInitialized {

    private lateinit var m: InjectedMembers
    override val _propertyInitializer = UnsafePropertyInitializer()

    private val actionsByEvent: Map<Class<out Event>, List<EventAction<*>>> by postInitialized {
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

    private val simpleActionsMap by postInitialized { actionsByEvent.filterValuesAreInstance(SimpleEventAction::class.java) }
    private val overridableActionsMap by postInitialized { actionsByEvent.filterValuesAreInstance(OverridableEventAction::class.java) }

    override fun _init() {
        super._init()
        with(m) {
            eventActions.forEach { it.init() }
        }
    }

    fun <E : Event> simpleActionsFor(eventType: Class<E>): List<SimpleEventAction<E>> = eventType.actionsFrom(simpleActionsMap)

    fun <E : Event> overridableActionsFor(eventType: Class<E>): List<OverridableEventAction<E>> = eventType.actionsFrom(overridableActionsMap)

    private inline fun <E : Event, A : EventAction<E>> Class<E>.actionsFrom(map: Map<Class<out Event>, List<EventAction<*>>>): List<A> {
        @Suppress("UNCHECKED_CAST")
        return map[this] as? List<A> ?: emptyList()
    }

    private inline fun Class<out Event>.loadSortedActionStates(actions: List<EventAction<*>>): List<EventActionState> = with(m) {
        persistenceContext.execute {
            if (eventActionStateRepository.count(queries.countBy(this@loadSortedActionStates)) == 0L) {
                actions.sortedByDescending { it.priority }.forEachIndexed { i, action ->
                    eventActionStateRepository.add(EventActionState(action, i))
                }
            }
            eventActionStateRepository.find(queries.sortedFor(this@loadSortedActionStates))
        }
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
        _init()
    }

    protected class InjectedMembers @Inject constructor(
            val persistenceContext: PersistenceContext,
            val eventActionStateRepository: EventActionStateRepository,
            val queries: EventActionStateQueries,
            val eventActions: MutableSet<EventAction<*>>
    )
    //endregion
}

//region Utils
private inline fun <K, reified V> Map<K, List<*>>.filterValuesAreInstance(type: Class<V>): Map<K, List<V>> {
    return mapValues { it.value.filterIsInstance<V>() }
}
//endregion