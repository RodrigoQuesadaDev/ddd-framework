package com.aticosoft.appointments.mobile.business.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 22/09/15.
 */
/*internal*/ abstract class Entity : PersistableObject<String> {

    override final var id: String
        private set
    override final var version: Long = 0
        private set

    constructor(entityContext: Context?) {
        id = entityContext?.identityGenerator?.generate() ?: ""
    }

    @Singleton
    /*internal*/ class Context @Inject protected constructor(
            val identityGenerator: IdentityGenerator
    )
}