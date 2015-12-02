package com.aticosoft.appointments.mobile.business.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 22/09/15.
 */
/*internal*/ abstract class Entity {

    var id: Long
        private set
    //TODO should this field be kept? Should it have a private set?
    var version: Long = 0
    @Transient private var previousValue: Entity? = null

    //TODO getting NullPointerException on FetchGroup:292 when using lateinit
    protected var context: Context by notNull()

    constructor(context: Context) {
        id = context.identityGenerator.generate()
        this.context = context
    }

    interface EntityStateReader {
        var Entity.previousValue: Entity?
            get() = this.previousValue
            set(value) {
                this.previousValue = value
            }
    }

    @Singleton
    /*internal*/ class Context @Inject constructor(val identityGenerator: IdentityGenerator)
}