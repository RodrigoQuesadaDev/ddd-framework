package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
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
    var priority: Int = action.priority
        private set
    var position: Int = position
        private set
    var executionCount: Int = 0
        private set
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