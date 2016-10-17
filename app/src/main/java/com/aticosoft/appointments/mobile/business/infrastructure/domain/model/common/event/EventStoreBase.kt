@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectFilteredChangeObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObservationFilter
import com.aticosoft.appointments.mobile.business.domain.model.common.event.*
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.QueryEntityForEvent
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.entityPath
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
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
/*internal*/ open class EventStoreBase<E : Event> @Inject protected constructor() : EventStore<E>, Event.ActionTrackingAccess {

    private lateinit var m: InjectedMembers<E>

    private val changeObserver by lazy { m.changeObserverFactory.create() }

    private val eventFilter by lazy { PersistableObjectObservationFilter(m.eventType) }

    protected open val simpleActions: List<SimpleEventAction<E>> by lazy {
        with(m) { eventActionsManager.simpleActionsFor(eventType) }
    }

    protected open val overridableActions: List<OverridableEventAction<E>> by lazy {
        with(m) { eventActionsManager.overridableActionsFor(eventType) }
    }

    protected var actionsSubscription: Subscription? = null

    private inline fun init() {
        resubscribeActions()
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
    private inline fun Observable<*>.repeatWhenChangeOccurs() = repeatWhenChangeOccurs(changeObserver.observe(arrayOf(eventFilter)))

    private inline fun executeEventActions(): Unit = with(m) {
        persistenceContext.execute {
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
        }
    }

    override fun add(event: E) {
        m.repository.add(event)
    }

    //region Queries
    @Singleton
    protected class Queries<E : Event> @Inject protected constructor(
            eventType: Class<E>
    ) : JdoQueries<E>() {

        private val e by lazy { QueryEntityForEvent(eventType.entityPath()) }

        fun firstEvent() = UniqueQuery {
            context.queryFactory.selectFrom(e).orderBy(e.id.asc()).fetchFirst()
        }
    }
    //endregion

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
        init()
    }

    protected class InjectedMembers<E : Event> @Inject protected constructor(
            val eventType: Class<E>,
            val repository: EventRepository<E>,
            val queries: Queries<E>,
            val eventActionsManager: EventActionsManager,
            val changeObserverFactory: PersistableObjectFilteredChangeObserver.Factory<E>,
            val persistenceContext: PersistenceContext,
            val stateRepository: EventActionStateRepository,
            val stateQueries: EventActionStateQueries
    )
    //endregion
}