package com.aticosoft.appointments.mobile.business.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.rodrigodev.common.properties.preventSetterCall

/**
 * Created by Rodrigo Quesada on 12/08/16.
 */
/*internal*/ abstract class Event : PersistableObject<Long>() {

    override var id: Long = 0
        set(value):Unit = preventSetterCall()
    override var version: Long = 0
        set(value):Unit = preventSetterCall()
}