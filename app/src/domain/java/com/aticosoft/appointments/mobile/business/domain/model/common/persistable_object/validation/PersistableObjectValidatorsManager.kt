@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class PersistableObjectValidatorsManager @Inject protected constructor(
        private val validators: MutableSet<PersistableObjectValidator<*, *>>,
        private val persistenceContext: PersistenceContext
) {
    fun registerValidators() = validators.forEach {
        persistenceContext.registerPersistableObjectLifecycleListener(it)
    }
}