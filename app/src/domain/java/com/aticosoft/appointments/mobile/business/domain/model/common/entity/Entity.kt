package com.aticosoft.appointments.mobile.business.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.rodrigodev.common.properties.preventSetterCall
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 22/09/15.
 */
/*internal*/ abstract class Entity : PersistableObject<String> {

    override final var id: String
        set(value) {
            //TODO change after KT-13592 gets fixed
            @Suppress("SENSELESS_COMPARISON")
            if (field != null) preventSetterCall()
            field = value
        }
    override final var version: Long = 0
        set(value):Unit = preventSetterCall()

    constructor(entityContext: Context?) {
        id = entityContext?.identityGenerator?.generate() ?: ""
    }

    @Singleton
    /*internal*/ class Context @Inject protected constructor(
            val identityGenerator: IdentityGenerator
    )
}