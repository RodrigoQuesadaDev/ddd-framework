@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.validation

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class EntityValidatorsManager @Inject protected constructor(
        private val entityValidators: MutableSet<EntityValidator<*, *>>,
        private val persistenceContext: PersistenceContext,
        private val entityValidatorContextProvider: Provider<EntityValidator.Context>
) {

    fun registerValidators() = entityValidators.forEach {
        it.initialize(entityValidatorContextProvider.get())
        persistenceContext.registerEntityLifecycleListener(it)
    }
}