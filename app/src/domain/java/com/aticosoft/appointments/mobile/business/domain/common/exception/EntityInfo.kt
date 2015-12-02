package com.aticosoft.appointments.mobile.business.domain.common.exception

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity

/**
 * Created by Rodrigo Quesada on 30/11/15.
 */
internal class EntityInfo(val entity: Entity) {

    override fun toString(): String {
        return "{id: ${entity.id}, version: ${entity.version}, type: ${entity.javaClass.simpleName}}"
    }
}