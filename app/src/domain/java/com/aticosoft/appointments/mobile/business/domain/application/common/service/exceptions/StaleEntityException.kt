package com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions

import com.aticosoft.appointments.mobile.business.domain.common.exception.EntityInfo
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.jdo.JDOObjectNotFoundException
import javax.jdo.JDOOptimisticVerificationException

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
/*internal*/ open class StaleEntityException : RuntimeException {

    constructor(cause: JDOOptimisticVerificationException) : super("A stale entity was used during the transaction.", cause)

    constructor(entity: Entity) : super(errorMessageFor("A stale entity was used during the transaction", entity))

    protected constructor(message: String, cause: Throwable) : super(message, cause)
}

/*internal*/ class NonExistingStaleEntityException : StaleEntityException {

    constructor(entity: Entity, cause: JDOObjectNotFoundException) : super(errorMessageFor("The entity doesn't exist anymore", entity), cause)
}

private fun errorMessageFor(text: String, entity: Entity) = "$text: ${EntityInfo(entity)}"