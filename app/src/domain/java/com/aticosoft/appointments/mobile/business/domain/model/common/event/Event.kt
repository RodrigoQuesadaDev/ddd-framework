package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject

/**
 * Created by Rodrigo Quesada on 12/08/16.
 */
/*internal*/ abstract class Event : PersistableObject<Long>() {

    override final var id: Long = 0
        private set
    override final var version: Long = 0
        private set
    private var actionTrackingPosition: Int = 0

    //TODO assign emptyBitSet?!!!
    //private val processedFlags: Long
    //TODO should it bee single boolean flag (only 1, not many flags?)
    //private val keepFlags: EWAHCompressedBitmap

    interface ActionTrackingAccess {

        var Event.actionTrackingPosition: Int
            get() = this.actionTrackingPosition
            set(value) {
                this.actionTrackingPosition = value
            }
    }
}