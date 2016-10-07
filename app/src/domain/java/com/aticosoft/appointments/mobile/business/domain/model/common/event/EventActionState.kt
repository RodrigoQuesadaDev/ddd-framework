package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
import com.rodrigodev.common.properties.preventSetterCall
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 30/09/16.
 */
@PersistenceCapable
/*internal*/ class EventActionState(action: EventAction<*>) : PersistableObject<Long>() {
    companion object {
        val DEFAULT_PRIORITY = 0
    }

    override var id: Long = 0
        private set
    override var version: Long = 0
        private set
    /*var type: Type = Type(action)
        set(value):Unit = preventSetterCall()*/
    var actionType: String = action.type
        set(value):Unit = preventSetterCall()
    var eventType: String = action.eventTypeId
        set(value):Unit = preventSetterCall()
    var priority: Int = DEFAULT_PRIORITY
        set(value):Unit = preventSetterCall()
    var position: Int = 0
        set(value):Unit = preventSetterCall()
    var executionCount: Int = 0
        set(value):Unit = preventSetterCall()

    /*class Type(action: EventAction<*>) {
        var actionType: String by writeOnce()
        var eventType: String by writeOnce()

        init {
            actionType = action.type
            eventType = action.eventTypeId
        }
    }*/
}

@Singleton
/*internal*/ class EventActionStateRepository @Inject protected constructor() : Repository<EventActionState, Long>, JdoRepository<EventActionState, Long>()