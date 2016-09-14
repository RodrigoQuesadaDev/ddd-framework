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
import rx.schedulers.Schedulers
import rx.util.async.Async.fromCallable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@Singleton
/*internal*/ open class EventStoreBase<E : Event> @Inject protected constructor() : EventStore<E> {

    private lateinit var m: InjectedMembers<E>

    private val changeObserver by lazy { m.changeObserverFactory.create() }

    private val eventFilter by lazy { PersistableObjectObservationFilter(m.eventType) }

    protected open val eventActions: Sequence<EventAction<E>> by lazy { m.eventActions.asSequence() }

    private inline fun init() {
        fromCallable(
                { executeEventActions() },
                Schedulers.io()
        )
                .repeatWhenChangeOccurs()
                .subscribe()
    }

    private inline fun Observable<*>.repeatWhenChangeOccurs() = repeatWhenChangeOccurs(changeObserver.observe(arrayOf(eventFilter)))

    private inline fun executeEventActions() = with(m) {
        //TODO implement this stuff correctly!!!
        //Probably group them first at start...
        //TODO optimize, move filtering to field declaration??? Also cache that sequence that is created?
        //TODO better yet, expose 2 properties called simpleActions and overridableActions... sequences... cached???
        this@EventStoreBase.eventActions.filterIsInstance<SimpleEventAction<E>>()
                .forEach { action ->
                    persistenceContext.execute {
                        repository.find(queries.firstEvent())?.let {
                            action.execute(it)
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
            val eventActions: MutableSet<EventAction<E>>,
            val changeObserverFactory: PersistableObjectFilteredChangeObserver.Factory<E>,
            val persistenceContext: PersistenceContext
    )
    //endregion
}