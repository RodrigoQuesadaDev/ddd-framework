package com.aticosoft.appointments.mobile.business.domain.common.exception

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject

/**
 * Created by Rodrigo Quesada on 30/11/15.
 */
internal class PersistableObjectInfo(val obj: PersistableObject<*>) {

    override fun toString(): String {
        return "{id: ${obj.id}, version: ${obj.version}, type: ${obj.javaClass.simpleName}}"
    }
}