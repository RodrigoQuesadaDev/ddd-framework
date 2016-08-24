package com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions

import com.aticosoft.appointments.mobile.business.domain.common.exception.PersistableObjectInfo
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
/*internal*/ class DirtyPersistableObjectException(obj: PersistableObject<*>) : RuntimeException("Persistable object is dirty: ${PersistableObjectInfo(obj)}")