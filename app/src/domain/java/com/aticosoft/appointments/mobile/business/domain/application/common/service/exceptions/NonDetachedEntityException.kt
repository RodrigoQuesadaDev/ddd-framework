package com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions

import com.aticosoft.appointments.mobile.business.domain.common.exception.EntityInfo
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
/*internal*/ class NonDetachedEntityException(entity: Entity) : RuntimeException("Entity is not detached: ${EntityInfo(entity)}")