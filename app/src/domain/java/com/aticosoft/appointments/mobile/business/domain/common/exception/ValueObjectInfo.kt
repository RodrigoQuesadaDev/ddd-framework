package com.aticosoft.appointments.mobile.business.domain.common.exception

/**
 * Created by Rodrigo Quesada on 30/11/15.
 */
internal class ValueObjectInfo(val obj: Any) {

    override fun toString(): String {
        return "{type: ${obj.javaClass.simpleName}}"
    }
}