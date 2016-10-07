package com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.exceptions

import com.aticosoft.appointments.mobile.business.domain.common.exception.ValueObjectInfo

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
/*internal*/ class ValueObjectModificationException(obj: Any) : RuntimeException("Value object was modified: ${ValueObjectInfo(obj)}")