@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.validation

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityValidators
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class EntityValidatorsManager @Inject protected constructor(private val c: Context) {

    fun registerValidators() = c.entityValidators.forEach {
        it.initialize(c.entityValidatorContextProvider.get())
        c.persistenceContext.registerEntityLifecycleListener(it)
    }

    class Context @Inject protected constructor(
            @EntityValidators val entityValidators: Array<EntityValidator<*>>,
            val persistenceContext: PersistenceContext,
            val entityValidatorContextProvider: Provider<EntityValidator.Context>
    )
}
