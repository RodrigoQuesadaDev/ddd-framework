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

    protected var context: Context by notNull()

    constructor(context: Context) {
        id = context.identityGenerator.generate()
        this.context = context
    }

    @Singleton
    /*internal*/ class Context @Inject constructor(val identityGenerator: IdentityGenerator)
}