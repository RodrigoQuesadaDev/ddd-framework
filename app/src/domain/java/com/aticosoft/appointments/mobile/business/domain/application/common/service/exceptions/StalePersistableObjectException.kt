package com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions

import com.aticosoft.appointments.mobile.business.domain.common.exception.PersistableObjectInfo
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import javax.jdo.JDOObjectNotFoundException
import javax.jdo.JDOOptimisticVerificationException

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
/*internal*/ open class StalePersistableObjectException : RuntimeException {

    constructor(cause: JDOOptimisticVerificationException) : super("A stale persistable object was used during the transaction.", cause)

    constructor(obj: PersistableObject<*>) : super(errorMessageFor("A stale persistable object was used during the transaction", obj))

    protected constructor(message: String, cause: Throwable) : super(message, cause)
}

/*internal*/ class NonExistingStalePersistableObjectException : StalePersistableObjectException {

    constructor(obj: PersistableObject<*>, cause: JDOObjectNotFoundException) : super(errorMessageFor("The persistable object doesn't exist anymore", obj), cause)
}

private fun errorMessageFor(text: String, obj: PersistableObject<*>) = "$text: ${PersistableObjectInfo(obj)}"