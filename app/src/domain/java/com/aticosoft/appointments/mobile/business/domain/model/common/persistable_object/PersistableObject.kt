package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

/**
 * Created by Rodrigo Quesada on 21/08/16.
 */
/*internal*/ abstract class PersistableObject<I> {

    abstract val id: I
    abstract val version: Long

    @Transient private var previousValue: PersistableObject<*>? = null

    //TODO making previousValue internal should be enough (problem is right now this class is not on a separate module)
    interface PersistableObjectStateAccess {
        var PersistableObject<*>.previousValue: PersistableObject<*>?
            get() = this.previousValue
            set(value) {
                this.previousValue = value
            }
    }
}