@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionType.Companion.typeString
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.*
import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
import com.querydsl.jdo.JDOQuery
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.EmbeddedOnly
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 30/09/16.
 */
@PersistenceCapable
/*internal*/ class EventActionState(action: EventAction<*>, position: Int) : PersistableObject<Long>() {

    override var id: Long = 0
        private set
    override var version: Long = 0
        private set
    var type: EventActionType = EventActionType(action)
        private set
    var position: Int = position
        private set
    var executionCount: Int = 0
        private set

    fun restExecutionCount() {
        executionCount = 0
    }

    fun incExecutionCount() {
        ++executionCount
    }
}

@ValueObject
@EmbeddedOnly
class EventActionType(action: EventAction<*>) {
    companion object {
        val EventAction<*>.typeString: String
            get() = javaClass.name

        val Class<out Event>.typeString: String
            get() = name
    }

    var actionType: String = action.typeString
        private set
    var eventType: String = action.eventType.typeString
        private set
}

@Singleton
/*internal*/ class EventActionStateRepository @Inject protected constructor() : Repository<EventActionState, Long>, JdoRepository<EventActionState, Long>()

@Singleton
class EventActionStateQueries @Inject protected constructor() : JdoQueries<EventActionState>() {

    fun countBy(eventType: Class<out Event>): CountQuery<EventActionState>
            = CountQuery { jdoQueryBy(eventType).fetchCount() }

    fun sortedFor(eventType: Class<out Event>): ListQuery<EventActionState> = ListQuery {
        val e = QEventActionState.eventActionState
        jdoQueryBy(eventType)
                .orderBy(e.position.asc())
                .fetch()
    }

    fun by(eventType: Class<out Event>): ListQuery<EventActionState>
            = ListQuery { jdoQueryBy(eventType).fetch() }

    private inline fun jdoQueryBy(eventType: Class<out Event>): JDOQuery<EventActionState> {
        val e = QEventActionState.eventActionState
        return context.queryFactory.selectFrom(e).where(e.type.eventType.eq(eventType.typeString))
    }

    fun by(action: EventAction<*>): UniqueQuery<EventActionState> = UniqueQuery {
        val e = QEventActionState.eventActionState
        context.queryFactory.selectFrom(e).where(e.type.actionType.eq(action.typeString)).fetchOne()
    }
}