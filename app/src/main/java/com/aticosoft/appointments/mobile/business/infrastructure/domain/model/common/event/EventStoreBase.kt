@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.Application
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectChangeEvent.EventType.UPDATE
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectFilteredAsyncChangeObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObservationFilter
import com.aticosoft.appointments.mobile.business.domain.model.common.event.*
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.exceptions.IllegallyModifiedEvent
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.QueryEntityForEvent
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.entityPath
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.properties.UnsafePostInitialized
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import com.rodrigodev.common.rx.repeatWhenChangeOccurs
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import rx.util.async.Async.fromCallable
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOHelper

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@Singleton
/*internal*/ open class EventStoreBase<E : Event> @Inject protected constructor() : EventStore<E>, UnsafePostInitialized {

    private lateinit var m: InjectedMembers<E>
    override val _propertyInitializer = UnsafePropertyInitializer()

    private val changeObserver by postInitialized { m.changeObserverFactory.create() }

    private val eventFilter by postInitialized { PersistableObjectObservationFilter(m.eventType) }

    protected open val simpleActions: List<SimpleEventAction<E>> by postInitialized {
        with(m) { eventActionsManager.simpleActionsFor(eventType) }
    }

    protected open val overridableActions: List<OverridableEventAction<E>> by postInitialized {
        with(m) { eventActionsManager.overridableActionsFor(eventType) }
    }

    protected var actionsSubscription: Subscription? = null

    override fun _postInit() = with(m) {
        super._postInit()
        resubscribeActions()
        processedEventsListener.subscribe()
    }

    protected fun resubscribeActions() {
        actionsSubscription?.unsubscribe()
        actionsSubscription = fromCallable(
                { executeEventActions() },
                Schedulers.io()
        )
                .repeatWhenChangeOccurs()
                .subscribe()
    }

    //TODO performance can be improved by using CREATE event filter AND local observable such as "eventsWereUpdatedObservable"
    //TODO REMOVE EVENTS should not trigger???!
    private inline fun Observable<*>.repeatWhenChangeOccurs() = repeatWhenChangeOccurs(changeObserver.observe(arrayOf(eventFilter)))

    private inline fun executeEventActions(): Unit = with(m) {
        /*persistenceContext.execute {
            repository.find(queries.firstEvent())?.let { event ->

                if (event.actionTrackingPosition == simpleActions.size) {
                    repository.remove(event)
                    stateRepository.find(stateQueries.by(eventType)).forEach(EventActionState::restExecutionCount)
                }
                else {
                    val action = simpleActions[event.actionTrackingPosition]
                    val actionState = stateRepository.find(stateQueries.by(action))!!

                    var eventWasModified = false
                    if (with(action) { event.conditionIsMet(actionState) }) {
                        if (application.testingMode && JDOHelper.isDirty(event)) throw IllegallyModifiedEvent(event)

                        action.execute(event)
                        actionState.incExecutionCount()
                        if (JDOHelper.isDirty(event)) {
                            event.actionTrackingPosition = 0
                            eventWasModified = true
                        }
                    }

                    if (!eventWasModified) ++event.actionTrackingPosition
                }
            }
        }*/
    }

    override fun add(event: E) {
        m.repository.add(event)
    }

    //region Queries
    @Singleton
    class Queries<E : Event> @Inject protected constructor(
            eventType: Class<E>
    ) : JdoQueries<E>() {

        private val e = QueryEntityForEvent(eventType.entityPath())

        fun firstEvent() = UniqueQuery {
            context.queryFactory.selectFrom(e).orderBy(e.id.asc()).fetchFirst()
        }
    }
    //endregion

    //region Injection
    @Inject
    protected fun _inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
        _postInit()
    }

    protected class InjectedMembers<E : Event> @Inject constructor(
            val processedEventsListener: ProcessedEventsListener<E>,
            val eventType: Class<E>,
            val repository: EventRepository<E>,
            val queries: Queries<E>,
            val eventActionsManager: EventActionsManager,
            val changeObserverFactory: PersistableObjectFilteredAsyncChangeObserver.Factory<E>,
            val persistenceContext: PersistenceContext,
            val stateRepository: EventActionStateRepository,
            val stateQueries: EventActionStateQueries,
            val application: Application<*, *>
    )
    //endregion
}

@Singleton
/*internal*/ class ProcessedEventsListener<E : Event> @Inject protected constructor(
        eventType: Class<E>,
        private val repository: EventRepository<E>,
        private val queries: Queries<E>,
        changeObserverFactory: PersistableObjectFilteredAsyncChangeObserver.Factory<E>
) {
    private val changeObserver = changeObserverFactory.create()

    private val eventFilter = PersistableObjectObservationFilter(eventType, UPDATE)

    //TODO resubscribable...
    //TODO ...and test when subscription is resumed after having totally processed events
    fun subscribe() {

        /*changeObserver.observe(arrayOf(eventFilter)).

        fromCallable(
                { executeEventActions() },
                Schedulers.io()
        )
                .repeatWhenChangeOccurs()
                .subscribe()*/
    }

    private inline fun removeNonKeptProcessedEvent() {
        repository.find(queries.nonKeptProcessedEvent())
    }

    //region Queries
    @Singleton
    class Queries<E : Event> @Inject protected constructor(
            eventType: Class<E>
    ) : JdoQueries<E>() {

        private val e = QueryEntityForEvent(eventType.entityPath())

        fun nonKeptProcessedEvent() = ListQuery {
            context.queryFactory.selectFrom(e).orderBy(e.id.asc()).fetch()
        }
    }
    //endregion
}