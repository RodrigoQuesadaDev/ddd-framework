package com.aticosoft.appointments.mobile.business.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 22/09/15.
 */
/*internal*/ abstract class Entity {

    var id: Long
        private set
    var version: Long = 0
        private set
    @Transient private var previousValue: Entity? = null

    constructor(entityContext: Context) {
        id = entityContext.identityGenerator.generate()
    }

    //TODO making previousValue internal should be enough (problem is right now this class is not on a separate module)
    interface EntityStateReader {
        var Entity.previousValue: Entity?
            get() = this.previousValue
            set(value) {
                this.previousValue = value
            }
    }

    @Singleton
    /*internal*/ class Context @Inject protected constructor(
            val identityGenerator: IdentityGenerator
    )
}